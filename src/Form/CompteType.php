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
use Symfony\Component\Form\Extension\Core\Type\FileType as TypeFileType;
use Symfony\Component\Validator\Constraints as Assert;

class CompteType extends AbstractType
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
            ]   , null, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Champs obligatoire']),
                    
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
            ]   , null, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Champs obligatoire']),
                    
                ]
            ])
            ->add('solde', null, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Le solde ne peut pas être nul']),
                    new Assert\GreaterThan([
                        'value' => 50,
                        'message' => 'Le solde doit être superieur a 50dt  !!'
                    ]),
                ]
            ])
           
         
            

            ->add('id_user', EntityType::class, [
                'class' => Client::class,
                'attr' => [
                    'data-ajax' => 'true', // Utilisation des attributs data-* pour stocker des données personnalisées
                ],
                'placeholder' => "client",
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('a')
                        ->orderBy('a.id', 'ASC');
                },
                'choice_label' => function (Client $client) {
                    return $client->getNom() . '  ' . $client->getPrenom() . ' - Cin : ' . $client->getCin();
                    
                },
                
            ]   , null, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Champs obligatoire']),
                    
                ]
            ])
            ->add('id_agence',EntityType::class, [
                'class' => Agence::class,
                'placeholder' => "Agence",
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('a')
                        ->orderBy('a.nom', 'ASC');
                },
                'choice_label' => function (Agence $agence) {
                    if ($agence->getIdChef()) {
                    $choice_label=$agence->getNom() . ' |  Matricule Chef d\'agence : ' .$agence->getIdChef()->getMatricule();
                }
                    else {
                        $choice_label=$agence->getNom();}
                        return  $choice_label;
                    }
                  
                    
                            ]   , null, [
                                'constraints' => [
                                    new Assert\NotBlank(['message' => 'Champs obligatoire']),
                                    
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
