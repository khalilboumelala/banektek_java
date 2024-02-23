<?php

namespace App\Form;

use App\Entity\Agence;
use App\Entity\Agent;
use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class AgenceType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('adresse')
            ->add('nom')
            ->add('num_tel')

            ->add('id_chef',EntityType::class, [
                'class' => Agent::class,
                'placeholder' => "Chef d'agence",
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('a')
                        ->orderBy('a.id', 'ASC');
                },
                'choice_label' => function (Agent $agent) {
                    // Assuming 'client' is the property in Compte entity that references the Client entity.
                    // Adjust this based on your actual entity structure.
                    return $agent->getNom()." ".$agent->getPrenom()."-".$agent->getId();
                },
                'required' => false,
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Agence::class,
        ]);
    }
}

/*
>add('date_transaction')
            ->add('montant')
            ->add('id_compte',EntityType::class, [
                'class' => Compte::class,
                'placeholder' => "Compte emetteur",
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('a')
                        ->orderBy('a.id', 'ASC');
                },
                'choice_label' => function (Compte $compte) {
                    // Assuming 'client' is the property in Compte entity that references the Client entity.
                    // Adjust this based on your actual entity structure.
                    return $compte->getIdUser()->getNom()." ".$compte->getIdUser()->getPrenom()."-".$compte->getIdUser()->getId();
                },
            ])
        
  ;  }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Transaction::class,
        ]);
    }
}

*/