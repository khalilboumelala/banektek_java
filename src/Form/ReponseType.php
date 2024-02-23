<?php

namespace App\Form;

use App\Entity\Agent;
use App\Entity\Reclamation;
use App\Entity\Reponse;
use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReponseType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('date_reponse')
            ->add('id_reclamation',EntityType::class, [
                'class' => Reclamation::class,
                'placeholder' => "ID Reclamation",
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('a')
                        ->orderBy('a.id', 'ASC');
                },
                'choice_label' => 'id', // Change 'nom' to the actual property representing the author's name.
            ])
            ->add('id_agent',EntityType::class, [
                'class' => Agent::class,
                'placeholder' => "ID agent",
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('a')
                        ->orderBy('a.id', 'ASC');
                },
                'choice_label' => 'id', // Change 'nom' to the actual property representing the author's name.
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Reponse::class,
        ]);
    }
}
