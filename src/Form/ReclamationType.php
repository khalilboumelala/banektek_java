<?php

namespace App\Form;

use App\Entity\Client;
use App\Entity\Reclamation;
use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\FileType;

use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ReclamationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
          
            ->add('email')
            ->add('type', ChoiceType::class, [
                'choices' => [
                    'Choisir un type' => '', // Placeholder option
                    'Problème de connexion' => 'probleme_connection',
                    'Problème de transaction' => 'probleme_transaction',
                    'Problème de fonctionnalité' => 'probleme_fonctionnalite',
                    'Service client insatisfaisant' => 'service_client_insatisfaisant',
                    'Problème de sécurité' => 'probleme_securite',
                    'Problème technique' => 'probleme_technique',
                    'Autre' => 'autre',
                ],
            ])
            
            ->add('description', TextareaType::class, [
                'attr' => ['rows' => 4], // Adjust the number of rows as needed
            ])
            ->add('document',FileType::class, [
                'label' => 'Documents',
                'required' => false, // Set to true if the image is mandatory
                'mapped' => false, // This tells Symfony not to try to map this field to any entity property)
                ])
          
            
          /*  ->add('id_client',EntityType::class, [
                'class' => Client::class,
                'placeholder' => "Id Client",
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('a')
                        ->orderBy('a.id', 'ASC');
                },
                'choice_label' => 'id', // Change 'nom' to the actual property representing the author's name.
            ])*/
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Reclamation::class,
        ]);
    }
}
