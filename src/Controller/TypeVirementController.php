<?php

namespace App\Controller;

use App\Entity\TypeVirement;
use App\Form\TypeVirementType;
use App\Repository\TypeVirementRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/types')]
class TypeVirementController extends AbstractController
{
    #[Route('/', name: 'app_type_virement_index', methods: ['GET', 'POST'])]
    public function index(TypeVirementRepository $typeVirementRepository,Request $request, EntityManagerInterface $entityManager): Response
    {$typeVirement = new TypeVirement();
        $form = $this->createForm(TypeVirementType::class, $typeVirement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($typeVirement);
            $entityManager->flush();

            return $this->redirectToRoute('app_type_virement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('type_virement/index.html.twig', [
            'types' => $typeVirementRepository->findAll(),

            'type_virement' => $typeVirement,
            'form' => $form,
        ]);
       
    }

    #[Route('/new', name: 'app_type_virement_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $typeVirement = new TypeVirement();
        $form = $this->createForm(TypeVirementType::class, $typeVirement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($typeVirement);
            $entityManager->flush();

            return $this->redirectToRoute('app_type_virement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('type_virement/new.html.twig', [
            'type_virement' => $typeVirement,
            'form' => $form,
        ]);
    }

    #[Route('/edit/{id}', name: 'app_type_virement_show', methods: ['GET'])]
    public function show(TypeVirement $typeVirement): Response
    {
        return $this->render('type_virement/show.html.twig', [
            'type_virement' => $typeVirement,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_type_virement_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, TypeVirement $typeVirement, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(TypeVirementType::class, $typeVirement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_type_virement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('type_virement/edit.html.twig', [
            'type_virement' => $typeVirement,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_type_virement_delete', methods: ['GET', 'POST'])]
    public function delete(Request $request, TypeVirement $typeVirement, EntityManagerInterface $entityManager): Response
    {
      
            $entityManager->remove($typeVirement);
            $entityManager->flush();
       

        return $this->redirectToRoute('app_type_virement_index', [], Response::HTTP_SEE_OTHER);
    }
}
