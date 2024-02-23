<?php

namespace App\Controller;

use App\Entity\Demande;
use App\Form\DemandeType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\BrowserKit\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/demande')]
class DemandeController extends AbstractController
{
    #[Route('/', name: 'app_demande')]
    public function index(): Response
    {
        return $this->render('demande/index.html.twig', [
            'controller_name' => 'DemandeController',
        ]);
    }
    #[Route('/new', name: 'demande_new')]
    public function new(Request $request): Response
    {
        $demande = new Demande();
        $form = $this->createForm(DemandeType::class, $demande);

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($demande);
            $entityManager->flush();

            // Rediriger l'utilisateur ou afficher un message de succès
        }

        return $this->redirectToRoute('app_client_index', [], Response::HTTP_SEE_OTHER);

    }
    
    #[Route('/supprimer/{id}', name: 'app_demande_delete', methods: ['POST'])]
    public function delete( Demande $demande, EntityManagerInterface $entityManager): Response
    {
            $entityManager->remove($demande);
            $entityManager->flush();
    

            return $this->redirectToRoute('app_client_index', [], Response::HTTP_SEE_OTHER);
        }

    #[Route('/valid/{id}', name: 'app_valid_delete', methods: ['POST'])]
    public function valid( EntityManagerInterface $entityManager, Demande $demande = null): Response
    {       $demande->setEtat("valider");
        
            $entityManager->flush();
            return $this->redirectToRoute('app_client_index', [], Response::HTTP_SEE_OTHER);
        }
}
