<?php

namespace App\Form;

use App\Entity\Agence;
use App\Entity\Client;
use App\Entity\Compte;
use Doctrine\DBAL\Types\DateType;
use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType as TypeDateType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CompteTypeUpdate extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
     
            ->add('type',ChoiceType::class, [
                'label' => 'Type',
                'choices' => [
                    
                    'Compte courant' => 'Compte courant',
                    "Compte d'épargne" => "Compte d'épargne",
                    'Compte bloqué' => 'Compte bloqué',
           
                ]
            ])
            ->add('etat',ChoiceType::class, [
                'label'=> 'Etat',
                'choices' => [
                    'Actif' => 'Actif',
                    'Inactif' => 'Inactif',
                    'Bloqué ' => 'Bloqué ',
                    'Fermé' => 'Fermé',
                    'Suspendu' => 'Suspendu',



                ]
            ])
            
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Compte::class,
        ]);
    }
}
