<?php

namespace App\Form;

use App\Entity\Client;
use Google\Cloud\Compute\V1\Enums\FileContentBuffer\FileType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\FileType as TypeFileType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints as Assert;
class ClientType extends AbstractType
{       
    

    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('dob', DateType::class, [
            'widget' => 'single_text',
            'data' => new \DateTime(),  
            'constraints' => [
                new Assert\NotBlank([
                    'message' => 'La date de début ne doit pas être vide.',
                ]),
            ],
        ])
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
            ->add('cin', null, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Le cin ne peut pas être vide']),
                    new Assert\Regex([
                        'pattern' => '/^\d{8}$/',
                        'message' => 'Le cin doit être composé de 8 chiffres.'
                    ])
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
            ->add('genre', ChoiceType::class, [
                'label' => 'Genre',
                'choices' => [
                    
                    'Homme' => 'Homme',
                    'Femme' => 'Femme',
                    'Autre' => 'Autre',
                ],
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => 'Non spécifié',
                ],
            ])
            ->add('pays', ChoiceType::class, [
                'choices' => [
                    'Tunisie' => 'Tunisie',
                    'Libye' => 'Libye',
                    'Algérie' => 'Algérie',
                    'Maroc' => 'Maroc',
                    'Égypte' => 'Égypte',
                    'France' => 'France',
                    'Italie' => 'Italie',
                    'Allemagne' => 'Allemagne',
                    'Canada' => 'Canada',
                    'Russie' => 'Russie',
                    'Afrique du Sud' => 'Afrique du Sud',
                    'Côte d\'Ivoire' => 'Côte d\'Ivoire',
                    // Ajoutez d'autres pays si nécessaire
                ],
                'data' => 'Tunisie', // Valeur par défaut
            ])
            ->add('adresse', null, [
    'constraints' => [
        new Assert\NotBlank([
            'message' => 'L\'adresse ne doit pas être vide.',
        ]),
        new Assert\Length([
            'min' => 5,
            'max' => 255,
            'minMessage' => 'L\'adresse doit avoir au moins {{ limit }} caractères.',
            'maxMessage' => 'L\'adresse ne peut pas dépasser {{ limit }} caractères.',
        ]),
        // Vous pouvez ajouter d'autres contraintes selon vos besoins
    ],
])
            ->add('email', null, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'L\'email ne peut pas être vide']),
                    new Assert\Email(['message' => 'L\'email "{{ value }}" n\'est pas valide.'])
                ]
            ])
            ->add('document')
            ->add('signature')
            ->add('profession', ChoiceType::class, [
                'label' => 'État Professionnel',
                'choices' => [
                    'Fonctionnaire/Public' => 'fonctionnaire',
                    'Indépendant/Entrepreneur' => 'independant',
                    'Profession libérale' => 'profession_libérale',
                    'Artiste/Créatif' => 'artiste',
                    'Freelancer' => 'freelancer',
                    'Retraité' => 'retraite',
                    'Etudiant' => 'etudiant',
                    'Autre' => 'autre',
                ],
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'etatProfessionnel',
                ],
            ])
            ->add('photo', TypeFileType::class, [
                'label' => 'Photo',
                'required' => false, // Si la photo n'est pas requise
                'mapped' => false, // N'est pas mappé directement sur l'entité
                'attr' => ['accept' => 'image/*'] // Accepte tous les types d'images
            ])            //->add('date_creation')
            //->add('username')
            //->add('password')
            //->add('last_login')
            //->add('etat')
            //->add('save', SubmitType::class, ['label' => 'Ajouter'])

        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Client::class,
         
        ]);
    }
    
}