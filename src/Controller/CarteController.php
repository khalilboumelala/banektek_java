<?php

namespace App\Controller;

use App\Entity\Agent;
use App\Entity\Carte;
use App\Entity\Client;
use App\Entity\Compte;
use App\Form\CarteType;
use App\Repository\CarteRepository;
use App\Service\TwilioService;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/carte')]
class CarteController extends AbstractController
{
    #[Route('/', name: 'app_carte_index', methods: ['GET'])]
    public function index(CarteRepository $carteRepository,Request $request,EntityManagerInterface $entityManager,SessionInterface $session): Response
    {  if($session->get('id_agent')){
        $id_agent_connecter = $session->get('id_agent');
        $agent_connecter = $entityManager->getRepository(Agent::class)->find($id_agent_connecter);
        $cartes = $carteRepository->findAll();
        $forms = [];
    
        foreach ($cartes as $carte) {
            $form = $this->createForm(carteType::class, $carte, [
                'action' => $this->generateUrl('app_carte_edit', ['id' => $carte->getId()]),
                'method' => 'POST',
            ]);
            $form->handleRequest($request);
    
            if ($form->isSubmitted() && $form->isValid()) {
                $entityManager->flush();
                $this->addFlash('success', 'carte updated successfully!');
            }
    
            $forms[] = $form->createView();
        }

        return $this->render('carte/index.html.twig', [
            'cartes' => $cartes,
            'forms'=> $forms,
            'agent_connecter' => $agent_connecter,

            
        ]);}
        else {
            return $this->redirectToRoute('app_agent_login');
        }
    }
    #[Route('/mycard', name: 'app_my_card', methods: ['GET'])]
public function mycard(CarteRepository $carteRepository, EntityManagerInterface $entityManager, SessionInterface $session): Response
{
    $userId = $session->get('id');

    // Récupérer l'utilisateur à partir de son ID
    $client = $entityManager->getRepository(Client::class)->find($userId);
    $comptes = $entityManager->getRepository(Compte::class)->findBy(['id_user' => $client]);
   $cartes = $carteRepository->findBy(['num_compte' => $comptes]);
    return $this->render('carte/mycard.html.twig', [
        'client' => $client,
        'cartes' => $cartes,
    ]);
}

private function generateRandomCVV(): int
{
    
    return random_int(100, 999);
}
    #[Route('/new', name: 'app_carte_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager,TwilioService $twilio,SessionInterface $session): Response
    {

        if($session->get('id_agent')){
            $id_agent_connecter = $session->get('id_agent');
            $agent_connecter = $entityManager->getRepository(Agent::class)->find($id_agent_connecter);
        ////////////

        ///////////
        $carte = new Carte();
        $form = $this->createForm(CarteType::class, $carte);
        $form->handleRequest($request);
        $carte->setDateEmission(new \DateTime());
        //////
        /////////////
        $dateExpiration = new \DateTime();
        $dateExpiration->modify('+5 years');
        $carte->setDateExpiration($dateExpiration);
        ///////////

        if ($form->isSubmitted() && $form->isValid()) {
            $carte->setCvv(random_int(100, 999));
            $carte->setPlafond(500);
            $entityManager->persist($carte);
            $entityManager->flush();
            $twilio->sendSMSOTP();
            return $this->redirectToRoute('app_carte_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('carte/new.html.twig', [
            'carte' => $carte,
            'form' => $form,
            'agent_connecter' => $agent_connecter,
        ]); } 
        else {
            return $this->redirectToRoute('app_agent_login');
        }
    }

    #[Route('/{id}', name: 'app_carte_show', methods: ['GET'])]
    public function show(Carte $carte): Response
    {
        return $this->render('carte/show.html.twig', [
            'carte' => $carte,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_carte_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Carte $carte, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(CarteType::class, $carte);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_carte_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('carte/edit.html.twig', [
            'carte' => $carte,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_carte_delete', methods: ['POST'])]
    public function delete(Request $request, Carte $carte, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$carte->getId(), $request->request->get('_token'))) {
            $entityManager->remove($carte);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_carte_index', [], Response::HTTP_SEE_OTHER);
    }
}
