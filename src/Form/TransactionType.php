<?php

namespace App\Form;

use App\Entity\Compte;
use App\Entity\Transaction;
use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\Choice;
use Symfony\Component\Validator\Constraints as Assert;

class TransactionType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder

        ->add('type', ChoiceType::class, [
            'label' => 'type',
            'choices' => [
               
                'Retrait' => 'Retrait',
                'Versement' => 'Versement',
               
            ] ]
            , null, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Champs obligatoire']),
                    
                ]
            ])
            
          
        ->add('montant', null, [
            'constraints' => [
                new Assert\NotBlank(['message' => 'Le montant ne peut pas être nul']),
                new Assert\GreaterThan([
                    'value' => 50,
                    'message' => 'Le montant doit être superieur a 10dt  !!'
                ]),
            ]
        ])
            ->add('id_compte',EntityType::class, [
                'class' => Compte::class,
                'placeholder' => "Compte emetteur",
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('a')
                        ->orderBy('a.id', 'ASC');
                },
                'choice_label' => function (Compte $compte) {
                    
                    return $compte->getIdUser()->getNom()." ".$compte->getIdUser()->getPrenom()."-".$compte->getIdUser()->getId();
                },
            ], null, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Champs obligatoire']),
                    
                ]
            ])
        
  ;  }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Transaction::class,
        ]);
    }
}
