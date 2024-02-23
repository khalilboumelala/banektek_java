<?php

namespace App\Form;

use App\Entity\Compte;
use App\Entity\Credit;
use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints as Assert;

class CreditType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('montant', null, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'le montant ne peut pas être vide']),
                    new Assert\GreaterThan([
                        'value' => 0,
                        'message' => 'Le montant doit etre positive  !!'
                    ])
                ]
            ])
            ->add('taux')
            ->add('duree', ChoiceType::class, [
                'label' => 'Durée de crédit',
                'choices' => [
                    '12 mois (1 année)' => 12,
                    '24 mois (2 années)' => 24,
                    '36 mois (3 années)' => 36,
                    '48 mois (4 années)' => 48,
                    '60 mois (5 années)' => 60,
                    '84 mois (7 années)' => 84,
                ],
            ])
          
            ->add('date_debut', DateType::class, [
                'widget' => 'single_text',
                'data' => new \DateTime(),  
                'constraints' => [
                    new Assert\NotBlank([
                        'message' => 'La date de début ne doit pas être vide.',
                    ]),
                ],
            ])
    
            
            
            ->add('type', ChoiceType::class, [
                'label' => 'Type de credit',
                'choices' => [
                    'Crédit commercial' => 'commercial',
                    'Crédit agricole' => 'agricole',
                    'Crédit automobile' => 'automobile',
                    'Crédit à la consommation' => 'consommation',
                    'Crédit hypothécaire' => 'hypothecaire',
                    'Crédit étudiant' => 'etudiant',
                ],
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => 'Non spécifié',
                ],
            ])
            ->add('apport_propre', null, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'L\'apport propre  ne doit pas être vide']),
                    new Assert\GreaterThan([
                        'value' => 0,
                        'message' => 'L\'apport propre doit etre positive  !!'
                    ])
                ]
            ])
            ->add('revenu_propre', null, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'le revenu_propre ne peut pas être vide']),
                    new Assert\GreaterThan([
                        'value' => 500,
                        'message' => 'Le revenu_propre doit etre superieur a 500dt   !!'
                    ])
                ]
            ])
            ->add('montant_echeance')
            ->add('date_echeance', ChoiceType::class, [
                'label' => 'Date d\'échéance',
                'choices' => array_combine(range(1, 25), range(1, 25)), // Creates choices from 1 to 25
                'placeholder' => "Jour de paiement de l'echeance", // Optional placeholder
                'required' => true, // Set it to true if you want the field to be required
                
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => 'Non spécifié',
                ],
            ])
            ->add('id_compte', EntityType::class, [
                'class' => Compte::class,
                'attr' => [
                    'data-ajax' => 'true', // Utilisation des attributs data-* pour stocker des données personnalisées
                ],
                'placeholder' => "Compte",
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('a')
                        ->orderBy('a.id', 'ASC');
                },
                'choice_label' => function (Compte $compte) {
                    return $compte->getIdUser()->getNom() . ' - ' . $compte->getRib();
                    // Vous devez remplacer getUser() par la méthode réelle qui retourne l'entité Client dans votre entité Compte.
                    
                },
                
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Credit::class,
        ]);
    }
}
