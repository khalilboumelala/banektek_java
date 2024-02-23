<?php

namespace App\Form;

use App\Entity\Agence;
use App\Entity\Agent;
use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType as TypeFileType;
///////////////
use Symfony\Component\Validator\Constraints as Assert;


class AgentType extends AbstractType
{

  
        public function buildForm(FormBuilderInterface $builder, array $options): void
        {
            $builder
                ->add('nom', null, [
                    'constraints' => [
                        new Assert\NotBlank(['message' => 'Le nom ne peut pas être vide']),
                        new Assert\Regex([
                            'pattern' => '/^[a-zA-ZÀ-ÿ\s]+$/',
                            'message' => 'Le nom ne peut contenir que des lettres.'
                        ]),
                        new Assert\Length([
                            'max' => 255,
                            'maxMessage' => 'Le nom ne peut pas dépasser {{ limit }} caractères.'
                        ])
                    ]
                ])
                ->add('prenom', null, [
                    'constraints' => [
                        new Assert\NotBlank(['message' => 'Le prénom ne peut pas être vide']),
                        new Assert\Regex([
                            'pattern' => '/^[a-zA-ZÀ-ÿ\s]+$/',
                            'message' => 'Le prénom ne peut contenir que des lettres.'
                        ]),
                        new Assert\Length([
                            'max' => 255,
                            'maxMessage' => 'Le prénom ne peut pas dépasser {{ limit }} caractères.'
                        ])
                    ]
                ])
                ->add('password', null, [
                    'constraints' => [
                        new Assert\NotBlank(['message' => 'Le mot de passe ne peut pas être vide']),
                        // Ajoutez d'autres contraintes de validation au besoin
                    ],
                ])
                ->add('faceid')
                ->add('poste', ChoiceType::class, [
                    'choices' => [
                        'Caissier' => 'Caissier',
                        'Conseiller clientèle ' => 'Conseiller clientèle ',
                        'Gestionnaire de comptes' => 'Gestionnaire de comptes',
                        'Gestionnaire de prêts' => 'Gestionnaire de prêts',
                        'Agent de services bancaires ' => 'Agent de services bancaires ',
                        'Chef d\'agence' => 'Chef d\'agence',
                        'Directeur' => 'Directeur',
                    ],
                    'constraints' => [
                        new Assert\NotBlank(['message' => 'Le poste ne peut pas être vide']),
                    ],
                ])
                ->add('email', null, [
                    'constraints' => [
                        new Assert\NotBlank(['message' => 'L\'email ne peut pas être vide']),
                        new Assert\Email(['message' => 'L\'email "{{ value }}" n\'est pas valide.'])
                    ]
                ])
                ->add('num_tel', null, [
                    'constraints' => [
                        new Assert\NotBlank(['message' => 'Le numéro de téléphone ne peut pas être vide']),
                        new Assert\Regex([
                            'pattern' => '/^\d{8}$/',
                            'message' => 'Le numéro de téléphone doit être composé de 8 chiffres.'
                        ])
                    ]
                ])
                ->add('photo', TypeFileType::class, [
                    'label' => 'Photo',
                    'required' => true, // La photo est obligatoire
                    'mapped' => false, // N'est pas mappé directement sur l'entité
                    'attr' => ['accept' => 'image/*'], // Accepte tous les types d'images
                    // Ajoutez les contraintes de validation si nécessaire
                ])
                ->add('id_agence', EntityType::class, [
                    'class' => Agence::class,
                    'placeholder' => "Agence ",
                    'required' => true, // L'agence est obligatoire
                    'query_builder' => function (EntityRepository $er) {
                        return $er->createQueryBuilder('a')
                            ->orderBy('a.id', 'ASC')
                            ->where('a.id_chef is null');
                    },
                    'choice_label' => function (Agence $agence) {
                        if (!$agence->getIdChef()) {
                            return $agence->getNom(). '  ' . $agence->getAdresse();
                        }
                    },
                    // Ajoutez les contraintes de validation si nécessaire
                ]);
        }
    
        public function configureOptions(OptionsResolver $resolver)
        {
            $resolver->setDefaults([
                'data_class' => Agent::class,
            ]);
        }
    }
    
    

