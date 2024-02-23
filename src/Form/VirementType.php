<?php

namespace App\Form;

use App\Entity\Compte;
use App\Entity\TypeVirement;
use App\Entity\Virement;
use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class VirementType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
           
            
            ->add('montant')
           
           
            ->add('type',EntityType::class, [
                'class' => TypeVirement::class,
                'placeholder' => "Type de virement",
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('a')
                        ->orderBy('a.id', 'ASC');
                },
                'choice_label' => 'nom', // Change 'nom' to the actual property representing the author's name.
            ])

            ->add('id_compte_emetteur',EntityType::class, [
                'class' => Compte::class,
                'placeholder' => "Compte emetteur",
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('a')
                        ->orderBy('a.id', 'ASC');
                },
                'choice_label' => function (Compte $compte) {
                    return $compte->getIdUser()->getNom() . '  ' . $compte->getIdUser()->getPrenom() . ' - Cin : ' . $compte->getRib();
                    
                },             ])
            ->add('id_compte_beneficiaire',EntityType::class, [
                'class' => Compte::class,
                'placeholder' => "Compte beneficiaire",
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('a')
                        ->orderBy('a.id', 'ASC');
                },
                'choice_label' => function (Compte $compte) {
                    return $compte->getIdUser()->getNom() . '  ' . $compte->getIdUser()->getPrenom() . ' - Cin : ' . $compte->getRib();
                    
                },            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Virement::class,
        ]);
    }
}
