<?php

namespace App\Form;

use App\Entity\Carte;
use App\Entity\Client;
use App\Entity\Compte;
use Doctrine\ORM\EntityRepository;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CarteType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
       
           
           
          
            
            ->add('type', ChoiceType::class, [
                'label' => 'type',
                'choices' => [
                  
                    'Carte Prépayée' => 'Carte Prépayée',
                    'Carte Débit' => ' Carte de Débit',
                    'Carte Crédit' => ' Carte de Crédit',
                    'Carte Retrait' => ' Carte de Retrait',
                   
                ] ])
            ->add('etat',ChoiceType::class,[
                'label'=>'etat',
                'choices'=>[
                    'En attente d\'activation'=>'En attente d\'activation',

                    'Activée '=>'Activée ',
                    'Désactivée '=>'Désactivée ',
                    'Bloquée'=>'Bloquée',
                    'Expirée'=>'Expirée',

                ]
            ])


         
            ->add('num_compte',EntityType::class, [
                'class' => Compte::class,
                'placeholder' => "Compte",
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('a')
                        ->orderBy('a.id', 'ASC');
                },
                'choice_label' => function (compte $compte) {
                    
                    return $compte->getIdUser()->getNom()." ".$compte->getIdUser()->getPrenom()."-".$compte->getIdUser()->getId();
                },
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Carte::class,
        ]);
    }
}
