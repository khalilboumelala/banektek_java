<?php

namespace App\Controller;

use App\Entity\Agent;
use App\Entity\Client;
use App\Entity\Compte;
use App\Form\CompteType;
use App\Form\CompteTypeUpdate;
use App\Repository\CompteRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;
use Twilio\Rest\Proxy\V1\Service\SessionInstance;

#[Route('/compte')]
class CompteController extends AbstractController
{
 
    #[Route('/', name: 'app_compte_index', methods: ['GET'])]
    public function index(compteRepository $compteRepository,Request $request,EntityManagerInterface $entityManager,SessionInterface $session): Response
    {    if($session->get('id_agent')){
        $id_agent_connecter = $session->get('id_agent');
        $agent_connecter = $entityManager->getRepository(Agent::class)->find($id_agent_connecter);
        $comptes = $compteRepository->findAll();
        $forms = [];
    
        foreach ($comptes as $compte) {
            $form = $this->createForm(CompteTypeUpdate::class, $compte, [
                'action' => $this->generateUrl('app_compte_edit', ['id' => $compte->getId()]),
                'method' => 'POST',
            ]);
            $form->handleRequest($request);
    
            if ($form->isSubmitted() && $form->isValid()) {
                $entityManager->flush();
                $this->addFlash('success', 'compte updated successfully!');
            }
    
            $forms[] = $form->createView();
        }
    
        return $this->render('compte/index.html.twig', [
            'comptes' => $comptes,
            'forms' => $forms,
            'agent_connecter' => $agent_connecter,
        ]);}else {
            return $this->redirectToRoute('app_agent_login');
        } 
    }

    #[Route('/new', name: 'app_compte_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager,SessionInterface $session): Response
    {     if($session->get('id_agent')){
        $id_agent_connecter = $session->get('id_agent');
        $agent_connecter = $entityManager->getRepository(Agent::class)->find($id_agent_connecter);
        $compte = new Compte();
        $form = $this->createForm(CompteType::class, $compte);
        $form->handleRequest($request);
      
        if ($form->isSubmitted() && $form->isValid()) {
            $compte->setDateCreation(new \DateTime());
            ////////////////////////////////////////
            $nom_agence = $compte->getIdAgence()->getNom();
            $nom_client = $compte->getIdUser()->getNom();
            $prenom_client = $compte->getIdUser()->getPrenom();
            $informations_concatenees = $nom_agence . $nom_client . $prenom_client;
            $rib=$this->genererRib($informations_concatenees);
            $compte->setRib(substr($rib,0,24));
            ////////////////////////////////////////
            $entityManager->persist($compte);
            $entityManager->flush();

            return $this->redirectToRoute('app_compte_index', [], Response::HTTP_SEE_OTHER);}
        

        return $this->renderForm('compte/new.html.twig', [
            'compte' => $compte,
            'form' => $form,
            'agent_connecter' => $agent_connecter,
        ]);}else {
            return $this->redirectToRoute('app_agent_login');
        }
    }

    #[Route('/mescomptes', name: 'mes_comptes', methods: ['GET', 'POST'])]
    public function mes_comptes(Request $request, EntityManagerInterface $entityManager,SessionInterface $session): Response
    {
        if($session->get('id')){
            $id_agent_connecter = $session->get('id');
            $client_connecter = $entityManager->getRepository(Client::class)->find($id_agent_connecter);
            $comptes=$client_connecter->getComptes();
            /////////////////////////////////////////:
            $historiqueSoldes = [];

            // Boucle à travers chaque compte
            foreach ($comptes as $compte) {
                $transactions = $compte->getTransactions();
                $solde = $compte->getSolde(); // Initialiser le solde à zéro pour chaque compte
                
                // Boucle à travers les transactions pour calculer le solde
           // Convertir la collection en un tableau
$transactionsArray = $transactions->toArray();

// Inverser l'ordre du tableau des transactions
$reversedTransactions = array_reverse($transactionsArray);

// Parcourir les transactions inversées
foreach ($reversedTransactions as $transaction) {
    // Votre logique pour calculer le solde ici
    if ($transaction->getType() == 'Versement') {
        $solde -= $transaction->getMontant();
    } else {
        $solde += $transaction->getMontant();
    }

                    
                // Ajouter l'historique des soldes à un tableau
                $historiqueSoldes[] = [
                    'compte' => $compte,
                    'historique' => $solde
                ]; }
            }
            //////////////////////////////////////////
        return $this->renderForm('compte/mescomptes.html.twig', [
         'client_connecter' => $client_connecter,
            'comptes' => $comptes,
            'historiqueSoldes' => $historiqueSoldes,


        ]); } 
        else {
            return $this->redirectToRoute('app_article_index');
        }
    }

    #[Route('/{id}', name: 'app_compte_show', methods: ['GET'])]
    public function show(Compte $compte): Response
    {
        return $this->render('compte/show.html.twig', [
            'compte' => $compte,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_compte_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Compte $compte, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(CompteTypeUpdate::class, $compte);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_compte_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('compte/edit.html.twig', [
            'compte' => $compte,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_compte_delete', methods: ['POST'])]
    public function delete(Request $request, Compte $compte, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$compte->getId(), $request->request->get('_token'))) {
            $entityManager->remove($compte);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_compte_index', [], Response::HTTP_SEE_OTHER);
    }
/*/////////////////////////////////////////////////////////*/
        

    /*//////////////////////////////////////////////////////////*/
    public function genererRib($informations_concatenees){
        $code_24_chiffres = md5($informations_concatenees);
        $code_24_chiffres= password_hash($code_24_chiffres,null);
                    $code_24_chiffres = preg_replace('/\D/', '', $code_24_chiffres); // Filtrer uniquement les chiffres
         
                    if(strlen($code_24_chiffres) < 12) {
                        $code_24_chiffres .= str_repeat('0',12 - strlen($code_24_chiffres)); // Ajouter des zéros supplémentaires si nécessaire
                    } elseif(strlen($code_24_chiffres) > 12) {
                        $code_24_chiffres = substr($code_24_chiffres, 0,12); // Tronquer le code si plus long que 24 chiffres
                    }
                    $code_24_chiffres2 = md5($informations_concatenees);
                    $code_24_chiffres2= password_hash($code_24_chiffres2,null);
                                $code_24_chiffres2 = preg_replace('/\D/', '', $code_24_chiffres2); // Filtrer uniquement les chiffres
                     
                                if(strlen($code_24_chiffres2) < 12) {
                                    $code_24_chiffres2 .= str_repeat('0',12 - strlen($code_24_chiffres2)); // Ajouter des zéros supplémentaires si nécessaire
                                } elseif(strlen($code_24_chiffres2) > 12) {
                                    $code_24_chiffres2 = substr($code_24_chiffres2, 0,12); // Tronquer le code si plus long que 24 chiffres
                                }     
                                return   $code_24_chiffres.$code_24_chiffres2;       }   
}
