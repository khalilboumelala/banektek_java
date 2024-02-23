<?php

namespace App\Controller;

use App\Entity\Credit;
use App\Entity\Echeance;
use App\Form\CreditType;
use App\Repository\CreditRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\Common\Collections\ArrayCollection;
use DateTime;
use Doctrine\Common\Collections\Criteria;

#[Route('/credit')]
class CreditController extends AbstractController
{

      /********************************************************************** */

      #[Route('/desactiver/{id}', name: 'credit_desactiver', methods: ['GET', 'POST'])]
      public function desactiver(Request $request, Credit $client = null, EntityManagerInterface $entityManager): Response
      {
          // Vérifier si l'entité Client existe
          if (!$client) {
              throw $this->createNotFoundException('Client non trouvé.');
          }
  
          // Modifier l'attribut etat en 'desactive'
          $client->setEtat('suspendu');
          $entityManager->flush();
  
          // Redirection vers la page index avec un message de succès
          $this->addFlash('success', 'Accès bloqué avec succès.');
          return $this->redirectToRoute('app_credit_index', [], Response::HTTP_SEE_OTHER);
      }
      ///////////////////////////
      #[Route('/activer/{id}', name: 'credit_activer', methods: ['GET', 'POST'])]
      public function activer(Request $request, Credit $client = null, EntityManagerInterface $entityManager): Response
      {
          // Vérifier si l'entité Client existe
          if (!$client) {
              throw $this->createNotFoundException('credit non trouvé.');
          }
  
          // Modifier l'attribut etat en 'desactive'
          $client->setEtat('en cours');
          $entityManager->flush();
  
          // Redirection vers la page index avec un message de succès
          $this->addFlash('success', 'Accès bloqué avec succès.');
          return $this->redirectToRoute('app_credit_index', [], Response::HTTP_SEE_OTHER);
      }
    #[Route('/', name: 'app_credit_index', methods: ['GET'])]
    public function index(CreditRepository $creditRepository,Request $request,EntityManagerInterface $entityManager): Response
    {
        $credits = $creditRepository->findAll();

        foreach ($credits as $credit) {
            $echeances = $credit->getEcheances();
    
            $criteria = Criteria::create()
                ->where(Criteria::expr()->eq('etat', 'en cours'))
                ->orderBy(['date' => Criteria::ASC])
                ->setMaxResults(5);
    
            $closestEcheances = $echeances->matching($criteria);
    
            // Assign the closestEcheances to the credit object
            $credit->setEcheances($closestEcheances);
        }
    
        return $this->render('credit/index.html.twig', [
            'credits' => $credits,
        ]);
    }
    #[Route('/mescredits', name: 'app_mes_credits', methods: ['GET'])]
    public function mes_credits(CreditRepository $creditRepository,Request $request,EntityManagerInterface $entityManager): Response
    {
        
        return $this->render('credit/mescredits.html.twig', [
            'credits' => $creditRepository->findAll(),
        ]);
    }
    #[Route('/new', name: 'app_credit_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $credit = new Credit();
        $form = $this->createForm(CreditType::class, $credit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            if($credit->getType()=="commercial")
                $credit->setTaux(0.09);
            elseif($credit->getType()=="agricole")
                $credit->setTaux(0.07);
            elseif($credit->getType()=="automobile")
                $credit->setTaux(0.1);
            elseif($credit->getType()=="consommation")
                $credit->setTaux(0.12);
            elseif($credit->getType()=="hypothecaire")
                $credit->setTaux(0.05);
            elseif($credit->getType()=="etudiant")
                $credit->setTaux(0.11);
            $montant_total=($credit->getMontant()-$credit->getApportPropre())*(1+$credit->getTaux()) ;
            $montant_echeance=$montant_total/$credit->getDuree();
            
            $montantEcheance = round($montant_echeance, 3); // Round the float to three decimal places
            $credit->setMontantEcheance($montantEcheance); // Pass the float value directly to the method
            $credit->setNbEcheancesRestants($credit->getDuree());
            
                        $credit->setNbEcheancesRestants($credit->getDuree());
            $credit->setEtat('en cours');


            $echeances = new ArrayCollection();
            $dateEcheance = new DateTime(); 
            $dateEcheance =  $credit->getDateDebut(); // Clone the date to avoid modifying the original object
            $dayOfMonth = $credit->getDateEcheance(); // Get the day from $credit->getDateEcheance()

            // Set the day of $dateEcheance to the value of $dayOfMonth
            $dateEcheance->setDate($dateEcheance->format('Y'), $dateEcheance->format('m'), $dayOfMonth);
            $credit->getIdCompte()->setSolde(($credit->getMontant()-$credit->getApportPropre())+$credit->getIdCompte()->getSolde());

            for ($i = 0; $i < $credit->getDuree(); $i++) {
                $echeance = new Echeance();
                $echeance->setIdCredit($credit);
                $echeance->setModePaiement('Your mode of payment'); // Set the mode of payment
                $echeance->setEtat('en cours'); // Set the state
                $echeance->setDate($dateEcheance);
                $entityManager->persist($echeance); // Persist each Echeance entity

                $dateEcheance = clone $dateEcheance;
                $dateEcheance->modify('+1 month'); // Increment date by 1 month

                $echeances->add($echeance);
            }

            // Add echeances to the credit
            $credit->setEcheances($echeances);
           
            $entityManager->persist($credit);
            $entityManager->flush();

            return $this->redirectToRoute('app_credit_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('credit/new.html.twig', [
            'credit' => $credit,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_credit_show', methods: ['GET'])]
    public function show(Credit $credit): Response
    {
        return $this->render('credit/show.html.twig', [
            'credit' => $credit,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_credit_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Credit $credit, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(CreditType::class, $credit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_credit_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('credit/edit.html.twig', [
            'credit' => $credit,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_credit_delete', methods: ['POST'])]
    public function delete(Request $request, Credit $credit, EntityManagerInterface $entityManager): Response
    {
        // Récupérer toutes les échéances liées à ce crédit
        $echeances = $credit->getEcheances();
    
        // Parcourir chaque échéance et les supprimer une par une
        foreach ($echeances as $echeance) {
            $entityManager->remove($echeance);
        }
    
        // Supprimer le crédit lui-même
        $entityManager->remove($credit);
    
        // Enregistrer les modifications dans la base de données
        $entityManager->flush();
    
        return $this->redirectToRoute('app_credit_index', [], Response::HTTP_SEE_OTHER);
    }
  
}
