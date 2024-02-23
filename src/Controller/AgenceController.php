<?php

namespace App\Controller;

use App\Entity\Agence;
use App\Form\AgenceType;
use App\Repository\AgenceRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/agence')]
class AgenceController extends AbstractController
{
    #[Route('/', name: 'app_agence_index', methods: ['GET'])]
    public function index(AgenceRepository $agenceRepository,Request $request, EntityManagerInterface $entityManager): Response
    {
        $agences = $agenceRepository->findAll();
        $forms = [];
        $currentDay = date('D');
        $currentTime = date("H:i");

    
        foreach ($agences as $agence) {
            $form = $this->createForm(AgenceType::class, $agence, [
                'action' => $this->generateUrl('app_agence_edit', ['id' => $agence->getId()]),
                'method' => 'POST',
            ]);
            $form->handleRequest($request);
    
            if ($form->isSubmitted() && $form->isValid()) {
                $entityManager->flush();
                $this->addFlash('success', 'Agence updated successfully!');
            }
    
            $forms[] = $form->createView();
        }
    
        return $this->render('agence/index.html.twig', [
            'agences' => $agences,
            'forms' => $forms,
            'current_day' => $currentDay,
            'current_time' => $currentTime,

        ]);
    }

    #[Route('/new', name: 'app_agence_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $agence = new Agence();
        $form = $this->createForm(AgenceType::class, $agence);
        $agence->setEtat("Normal");
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($agence);
            $entityManager->flush();

            return $this->redirectToRoute('app_agence_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('agence/new.html.twig', [
            'agence' => $agence,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_agence_show', methods: ['GET'])]
    public function show(Agence $agence): Response
    {
        return $this->render('agence/show.html.twig', [
            'agence' => $agence,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_agence_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Agence $agence, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(AgenceType::class, $agence);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_agence_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('agence/edit.html.twig', [
            'agence' => $agence,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_agence_delete', methods: ['POST'])]
    public function delete(Request $request, Agence $agence, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$agence->getId(), $request->request->get('_token'))) {
            $entityManager->remove($agence);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_agence_index', [], Response::HTTP_SEE_OTHER);
    }


    #[Route('/desactiver/{id}', name: 'agence_desactiver', methods: ['GET', 'POST'])]   
    public function desactiver(Request $request, Agence $agence = null, EntityManagerInterface $entityManager): Response
    {
        if ($agence) {
            $agence->setEtat("Desactive");
            $entityManager->flush();
            $this->addFlash('success', 'Agence desactiver avec success!');
        } else {
            $this->addFlash('error', 'Agence not found!');
        }

        return $this->redirectToRoute('app_agence_index');
    }

    #[Route('/activer/{id}', name: 'agence_activer', methods: ['GET', 'POST'])]
    public function activer(Request $request, Agence $agence = null, EntityManagerInterface $entityManager): Response
    {
        if ($agence) {
            $agence->setEtat("Normal");
            $entityManager->flush();
            $this->addFlash('success', 'Agence activer avec success!');
        } else {
            $this->addFlash('error', 'Agence not found!');
        }

        return $this->redirectToRoute('app_agence_index');
    }
    
}
