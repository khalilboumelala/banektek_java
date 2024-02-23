<?php

namespace App\Controller;

use App\Entity\Client;
use App\Entity\Compte;
use App\Entity\Virement;
use App\Form\VirementType;
use App\Repository\VirementRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/virement')]
class VirementController extends AbstractController
{
    #[Route('/mesvirements', name: 'app_mes_virement', methods: ['GET'])]
    public function mesvirements( EntityManagerInterface $entityManager,SessionInterface $session): Response
    {        if($session->get('id')){
        $id_client_connecter = $session->get('id');
        $client_connecter = $entityManager->getRepository(Client::class)->find($id_client_connecter);
       #$client_connecter = $entityManager->getRepository(Client::class)->find($id_agent_connecter);
       #$comptes=$client_connecter->getComptes();
        $comptes = $entityManager->getRepository(Compte::class)->findBy(['id_user' => $client_connecter->getId()]);


        return $this->render('virement/mesvirements.html.twig', [
         'comptes' => $comptes
        ]);}
        else {
            return $this->redirectToRoute('app_article_index', [], Response::HTTP_SEE_OTHER);

        } }
    #[Route('/', name: 'app_virement_index', methods: ['GET'])]
    public function index(virementRepository $virementRepository,Request $request,EntityManagerInterface $entityManager): Response
    {
        $virements = $virementRepository->findAll();
        $forms = [];
    
        foreach ($virements as $virement) {
            $form = $this->createForm(virementType::class, $virement, [
                'action' => $this->generateUrl('app_virement_edit', ['id' => $virement->getId()]),
                'method' => 'POST',
            ]);
            $form->handleRequest($request);
    
            if ($form->isSubmitted() && $form->isValid()) {
                $entityManager->flush();
                $this->addFlash('success', 'virement updated successfully!');
            }
    
            $forms[] = $form->createView();
        }
    
        return $this->render('virement/index.html.twig', [
            'virements' => $virements,
            'forms' => $forms,
        ]);
    }

    #[Route('/new', name: 'app_virement_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $virement = new Virement();
        $form = $this->createForm(VirementType::class, $virement);
        $virement->setDateEmission(new \DateTime());
        $virement->setDateApprobation(new \DateTime());
     
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($virement);
            $entityManager->flush();

            return $this->redirectToRoute('app_virement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('virement/new.html.twig', [
            'virement' => $virement,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_virement_show', methods: ['GET'])]
    public function show(Virement $virement): Response
    {
        return $this->render('virement/show.html.twig', [
            'virement' => $virement,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_virement_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Virement $virement, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(VirementType::class, $virement);
       
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_virement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('virement/edit.html.twig', [
            'virement' => $virement,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_virement_delete', methods: ['POST'])]
    public function delete(Request $request, Virement $virement, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$virement->getId(), $request->request->get('_token'))) {
            $entityManager->remove($virement);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_virement_index', [], Response::HTTP_SEE_OTHER);
    }
}
