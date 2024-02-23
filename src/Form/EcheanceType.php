<?php

namespace App\Form;

use App\Entity\Credit;
use App\Entity\Echeance;
use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class EcheanceType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('mode_paiement')
            ->add('etat')
            ->add('id_credit',EntityType::class, [
                'class' => Credit::class,
                'placeholder' => "Id Credit",
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
            'data_class' => Echeance::class,
        ]);
    }
}
