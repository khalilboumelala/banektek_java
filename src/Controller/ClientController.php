<?php

namespace App\Controller;

use App\Entity\Agent;
use App\Entity\Client;
use App\Entity\Demande;
use App\Form\ClientType;
use App\Form\DemandeType;
use App\Repository\ClientRepository;
use App\Service\TwilioService;

use Doctrine\ORM\EntityManager;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;




#[Route('/client')]
class ClientController extends AbstractController
{
    #[Route('/changement-mot-de-passe', name: 'client_changement_mot_de_passe', methods: ['POST'])]
    public function changerMotDePasse(Request $request, SessionInterface $session, EntityManagerInterface $entityManager): Response
    {
        // Récupérer l'ID du client connecté depuis la session
        $id_client_connecter = $session->get('id');
        $client_connecter = $entityManager->getRepository(Client::class)->find($id_client_connecter);
        
        // Récupérer les données du formulaire
        $ancienMotDePasse = $request->request->get('ancien_mot_de_passe');
        $nouveauMotDePasse = $request->request->get('nouveau_mot_de_passe');
        $confirmationMotDePasse = $request->request->get('confirmation_mot_de_passe');
        
        // Vérifier si l'ancien mot de passe est correct
        if (password_verify($ancienMotDePasse, $client_connecter->getPassword())) {
            // Vérifier si les nouveaux mots de passe correspondent
            if ($nouveauMotDePasse === $confirmationMotDePasse) {
                // Hasher le nouveau mot de passe
                $nouveauMotDePasseHash = password_hash($nouveauMotDePasse, PASSWORD_DEFAULT);
                
                // Mettre à jour le mot de passe dans l'entité Client
                $client_connecter->setPassword($nouveauMotDePasseHash);
                
                // Enregistrer les modifications
                $entityManager->flush();
                
                // Rediriger avec un message de succès
                $this->addFlash('success', 'Mot de passe modifié avec succès.');
            } else {
                $this->addFlash('error', 'Les nouveaux mots de passe ne correspondent pas.');
            }
        } else {
            $this->addFlash('error', 'Ancien mot de passe incorrect.');
        }
        
        // Rediriger vers la page de profil
        return $this->redirectToRoute('client_profil');
    }
    
    #[Route('/profil', name: 'client_profil', methods: ['GET', 'POST'])]
    public function profil(Request $request, SessionInterface $session, EntityManagerInterface $entityManager): Response
    {
        if ($session->get('id')) {
            $idClientConnecte = $session->get('id');
            $clientConnecte = $entityManager->getRepository(Client::class)->find($idClientConnecte);
    
            // Créer une nouvelle demande
            $demande = new Demande();
            $form = $this->createForm(DemandeType::class, $demande);
            $form->handleRequest($request);
    
            if ($form->isSubmitted() && $form->isValid()) {
                // Associer le client connecté à la nouvelle demande
                $demande->setClient($clientConnecte);
                
                // Ajouter la date de la demande si ce n'est pas géré automatiquement
                $demande->setDate(new \DateTime());
    
                // Persister la demande en base de données
                $entityManager->persist($demande);
                $entityManager->flush();
    
                // Ajouter un message flash pour indiquer le succès de l'opération
                $this->addFlash('success', 'Votre demande a été soumise avec succès!');
    
                // Rediriger l'utilisateur vers une route, si nécessaire
return $this->redirectToRoute('client_profil');            }
    
            return $this->render('client/profil.html.twig', [
                'form' => $form->createView(),
                'client_connecter' => $clientConnecte,
            ]);
        } else {
            return $this->redirectToRoute('app_article_index');
        }
    }
    
    #[Route('/', name: 'app_client_index', methods: ['GET'])]
    public function index(ClientRepository $clientRepository,Request $request,EntityManagerInterface $entityManager, SessionInterface $session): Response
    {
        if($session->get('id_agent')){
            $id_agent_connecter = $session->get('id_agent');
            $agent_connecter = $entityManager->getRepository(Agent::class)->find($id_agent_connecter);
            $demandes = $entityManager->getRepository(Demande::class)->findAll();


            $demandesEnAttente = array_filter($demandes, function($demande) {
                return $demande->getEtat() === 'en attente';
            });
    
            // Compter le nombre de demandes en attente
            $nombreDemandesEnAttente = count($demandesEnAttente);

        $clients = $clientRepository->findAll();
        $forms = [];
    
        foreach ($clients as $client) {
            $form = $this->createForm(ClientType::class, $client, [
                'action' => $this->generateUrl('app_client_edit', ['id' => $client->getId()]),
                'method' => 'POST',
            ]);
            $form->handleRequest($request);
    
            if ($form->isSubmitted() && $form->isValid()) {
                $entityManager->flush();
                $this->addFlash('success', 'Client updated successfully!');
            }
    
            $forms[] = $form->createView();
        }
    
            return $this->render('client/index.html.twig', [
                'demandes' => $demandes,
                'clients' => $clients,
                'forms' => $forms,
                'agent_connecter' => $agent_connecter,
                'nombreDemandesEnAttente' => $nombreDemandesEnAttente,
            ]); } 
        else {
            return $this->redirectToRoute('app_agent_login');

        }
    }

  
    #[Route('/new', name: 'app_client_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager,SessionInterface $session, TwilioService $twilioService): Response
    {    if($session->get('id_agent')){
        $id_agent_connecter = $session->get('id_agent');
        $agent_connecter = $entityManager->getRepository(Agent::class)->find($id_agent_connecter);
        $client = new Client();
        $form = $this->createForm(ClientType::class, $client);
        $form->handleRequest($request);
            //phone verif//
            $OTP = null;

        if ($form->isSubmitted() && $form->isValid()) {

            ////////////////PHONE//////////
            $phone = $client->getNumTel();
            $OTP = random_int(101, 909);
            $session->set('otp', $OTP);
            $session->set('phone', $phone);

            //$twilioService->sendVoiceOTP($phone, $OTP);
                ////////////////PHOTO//////////
                $tempFilePath = $form['photo']->getData();
                $randomNumber = rand(10000, 99999); // Génère un nombre aléatoire entre 10000 et 99999
                $destinationPath = "clients_photos/" . $client->getNom() . $client->getPrenom() . "_" . $randomNumber . ".png";
                
                $compressionQuality = 100;
        
                $this->compressImage($tempFilePath, $destinationPath, $compressionQuality);
        
                $client->setPhoto($destinationPath);
            
                ////////////////PHOTO//////////
                
    $yearOfBirth = $client->getDob()->format('Y');
    $username=  strtolower(substr($client->getPrenom(),0,3)) . strtolower(substr($client->getNom(),0,3)) .substr($yearOfBirth, -2)
    . substr($client->getCin(), 3, 1) . substr($client->getCin(), 6, 1) . substr($client->getCin(), 0, 1);

            

    $client->setUsername($username);

            $entityManager->persist($client);
            $entityManager->flush();

            return $this->redirectToRoute('app_client_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('client/new.html.twig', [
            'client' => $client,
            'form' => $form,
            'agent_connecter' => $agent_connecter,
        ]); } 
        else {
            return $this->redirectToRoute('app_agent_login');

        }
    }

    #[Route('/{id}/edit', name: 'app_client_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Client $client, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(ClientType::class, $client);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            if ($form->get('photo')->getData() !== null) {
                $ancienCheminImage = $client->getPhoto();
                if ($ancienCheminImage !== null && file_exists($ancienCheminImage)) {
                    unlink($ancienCheminImage);
                }
    
                $tempFilePath = $form['photo']->getData();
                $randomNumber = rand(10000, 99999); // Génère un nombre aléatoire entre 10000 et 99999
                $destinationPath = "clients_photos/" . $client->getNom() . $client->getPrenom() . "_" . $randomNumber . ".png";
                
                $compressionQuality = 100;
                $this->compressImage($tempFilePath, $destinationPath, $compressionQuality);
                $client->setPhoto($destinationPath);
            }
            $entityManager->flush();

            return $this->redirectToRoute('app_client_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->redirectToRoute('app_client_index', [], Response::HTTP_SEE_OTHER);

    }
   

    
      
  
    #[Route('/{id}', name: 'app_client_delete', methods: ['POST'])]
    public function delete(Request $request, Client $client, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$client->getId(), $request->request->get('_token'))) {
            $entityManager->remove($client);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_client_index', [], Response::HTTP_SEE_OTHER);
    }
    /////////////////////////////

    #[Route('/desactiver/{id}', name: 'client_desactiver', methods: ['GET', 'POST'])]
    public function desactiver(Request $request, Client $client = null, EntityManagerInterface $entityManager): Response
    {
        // Vérifier si l'entité Client existe
        if (!$client) {
            throw $this->createNotFoundException('Client non trouvé.');
        }

        // Modifier l'attribut etat en 'desactive'
        $client->setEtat('desactiver');
        $entityManager->flush();

        // Redirection vers la page index avec un message de succès
        $this->addFlash('success', 'Accès bloqué avec succès.');
        return $this->redirectToRoute('app_client_index', [], Response::HTTP_SEE_OTHER);
    }
    ///////////////////////////
    #[Route('/activer/{id}', name: 'client_activer', methods: ['GET', 'POST'])]
    public function activer(Request $request, Client $client = null, EntityManagerInterface $entityManager): Response
    {
        // Vérifier si l'entité Client existe
        if (!$client) {
            throw $this->createNotFoundException('Client non trouvé.');
        }

        // Modifier l'attribut etat en 'desactive'
        $client->setEtat('activer');
        $entityManager->flush();

        // Redirection vers la page index avec un message de succès
        $this->addFlash('success', 'Accès bloqué avec succès.');
        return $this->redirectToRoute('app_client_index', [], Response::HTTP_SEE_OTHER);
    }
    private  function compressImage($source, $destination, $quality) {
        $info = getimagesize($source);
        
        if ($info['mime'] == 'image/jpeg') {
            $image = imagecreatefromjpeg($source);
        } elseif ($info['mime'] == 'image/png') {
            $image = imagecreatefrompng($source);
        } elseif ($info['mime'] == 'image/gif') {
            $image = imagecreatefromgif($source);
        } else {
            return false;
        }    // Sauvegarder l'image compressée
           imagejpeg($image, $destination, $quality);
           
           // Libérer la mémoire
           imagedestroy($image);
           
           return true;
       }


}
