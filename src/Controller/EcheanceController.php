<?php

namespace App\Controller;

use App\Entity\Echeance;
use App\Form\EcheanceType;
use App\Repository\EcheanceRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/echeance')]
class EcheanceController extends AbstractController
{
    #[Route('/', name: 'app_echeance_index', methods: ['GET'])]
    public function index(EcheanceRepository $echeanceRepository): Response
    {
        
        return $this->render('echeance/index.html.twig', [
            'echeances' => $echeanceRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_echeance_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $echeance = new Echeance();
        $form = $this->createForm(EcheanceType::class, $echeance);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($echeance);
            $entityManager->flush();

            return $this->redirectToRoute('app_echeance_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('echeance/new.html.twig', [
            'echeance' => $echeance,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_echeance_show', methods: ['GET'])]
    public function show(Echeance $echeance): Response
    {
        return $this->render('echeance/show.html.twig', [
            'echeance' => $echeance,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_echeance_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Echeance $echeance, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(EcheanceType::class, $echeance);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_echeance_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('echeance/edit.html.twig', [
            'echeance' => $echeance,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_echeance_delete', methods: ['POST'])]
    public function delete(Request $request, Echeance $echeance, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$echeance->getId(), $request->request->get('_token'))) {
            $entityManager->remove($echeance);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_echeance_index', [], Response::HTTP_SEE_OTHER);
    }

    #[Route('/payer/{id}', name: 'echeance_payer', methods: ['GET', 'POST'])]
    public function payer(Request $request, Echeance $echeance = null, EntityManagerInterface $entityManager): Response
    {
        // Modifier l'attribut etat en 'paye'
        $echeance->setEtat('paye');
        $entityManager->flush();

        // Redirection vers la page index avec un message de succès
        //$this->addFlash('success', 'Accès bloqué avec succès.');
        return $this->redirectToRoute('app_credit_index', [], Response::HTTP_SEE_OTHER);
    }
   


}
