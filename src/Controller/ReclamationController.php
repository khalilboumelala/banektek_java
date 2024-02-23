<?php

namespace App\Controller;

use App\Entity\Client;
use App\Entity\Reclamation;
use App\Form\ReclamationType;
use App\Form\ReclamationTypeClient;
use App\Repository\ReclamationRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


use Symfony\Component\HttpFoundation\Session\SessionInterface;

#[Route('/reclamation')]
class ReclamationController extends AbstractController
{
  
    #[Route('/', name: 'app_reclamation_index', methods: ['GET'])]
    public function index(ReclamationRepository $reclamationRepository, Request $request, EntityManagerInterface $entityManager): Response
    {
        $reclamations = $reclamationRepository->findAll();
        $forms = [];
    
        foreach ($reclamations as $reclamation) {
            $form = $this->createForm(ReclamationType::class, $reclamation, [
                'action' => $this->generateUrl('app_reclamation_edit', ['id' => $reclamation->getId()]),
                'method' => 'POST',
            ]);
            $form->handleRequest($request);
    
            if ($form->isSubmitted() && $form->isValid()) {
                $entityManager->flush();
                $this->addFlash('success', 'Reclamation updated successfully!');
            }
    
            $forms[] = $form->createView();
        }
    
        return $this->render('reclamation/index.html.twig', [
            'reclamations' => $reclamations,
            'forms' => $forms,
        ]);
    }
    

    #[Route('/new', name: 'app_reclamation_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $reclamation = new Reclamation();
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $reclamation->setEtat("En Attente");
        $reclamation->setDateReclamation(( new \DateTime()));

       
        
        //$reclamation->setIdClient();
        
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $tempFilePath = $form['document']->getData();
            $destinationPath = "uploads/" .$reclamation->getType().$reclamation->getId().".png";
            $compressionQuality = 100;
    
            $this->compressImage($tempFilePath, $destinationPath, $compressionQuality);
    
            $reclamation->setDocument($destinationPath);


            $entityManager->persist($reclamation);
            $entityManager->flush();

            return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reclamation/new.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form,
        ]);
    }



    #[Route('/newClient', name: 'app_reclamation_newclient', methods: ['GET', 'POST'])]
    public function newClient(Request $request, EntityManagerInterface $entityManager): Response
    {
        $reclamation = new Reclamation();
        $form = $this->createForm(ReclamationTypeClient::class, $reclamation);
      
        
        //$reclamation->setIdClient();
        
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
//
     
        
        $reclamation->setEtat("En attente");
        $reclamation->setDateReclamation(( new \DateTime()));
        $session = $request->getSession();
        $username = $session->get('username');
        $client = $this->getDoctrine()->getRepository(Client::class)->findOneBy(['username' => $username]);
        $reclamation->setIdClient($client);
        $email= $client->getEmail();
        $reclamation->setEmail($email);
       
//

        
            $tempFilePath = $form['document']->getData();


            $lastReclamation = $entityManager->getRepository(Reclamation::class)->findOneBy([], ['id' => 'DESC']);
            $newId = $lastReclamation ? $lastReclamation->getId() + 1 : 1;
            $reclamation->setId($newId);

            $destinationPath = "uploads/" . $reclamation->getType() . "-".$reclamation->getId(). "-" . $reclamation->getDateReclamation()->format('Y-m-d-H-i') . ".png";

            $compressionQuality = 100;
    
            $this->compressImage($tempFilePath, $destinationPath, $compressionQuality);
    
            $reclamation->setDocument($destinationPath);


            $entityManager->persist($reclamation);
            $entityManager->flush();

            return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reclamation/newclient.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form,
        ]);
    }


    
    #[Route('/{id}', name: 'app_reclamation_show', methods: ['GET'])]
    public function show(Reclamation $reclamation): Response
    {
        return $this->render('reclamation/show.html.twig', [
            'reclamation' => $reclamation,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_reclamation_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Reclamation $reclamation, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reclamation/edit.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_reclamation_delete', methods: ['POST'])]
    public function delete(Request $request, Reclamation $reclamation, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reclamation->getId(), $request->request->get('_token'))) {
            $entityManager->remove($reclamation);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
    }


    #[Route('/desactiver/{id}', name: 'reclamation_desactiver', methods: ['GET', 'POST'])]
    public function desactiver(Request $request, Reclamation $reclamation = null, EntityManagerInterface $entityManager): Response
    {
       

        // Modifier l'attribut etat en 'desactive'
        $reclamation->setEtat('En cours');
        $entityManager->flush();

        // Redirection vers la page index avec un message de succès
       // $this->addFlash('success', 'Accès bloqué avec succès.');
        return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
    }
    ///////////////////////////
    #[Route('/activer/{id}', name: 'reclamation_activer', methods: ['GET', 'POST'])]
    public function activer(Request $request, Reclamation $reclamation = null, EntityManagerInterface $entityManager): Response
    {
       

        // Modifier l'attribut etat en 'desactive'
        if ($reclamation->getEtat() == 'En attente'){
            $reclamation->setEtat('En cours');
        }
        else{
            $reclamation->setEtat('Termine');
        }
        
        $entityManager->flush();

        // Redirection vers la page index avec un message de succès
        //$this->addFlash('success', 'Accès bloqué avec succès.');
        return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
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
