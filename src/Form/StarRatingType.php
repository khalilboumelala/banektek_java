<?php
// src/Form/Type/StarRatingType.php
namespace App\Form\Type;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\OptionsResolver\OptionsResolver;

class StarRatingType extends AbstractType
{
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'choices' => [
                '☆☆☆☆☆' => 5,
                '☆☆☆☆' => 4,
                '☆☆☆' => 3,
                '☆☆' => 2,
                '☆' => 1,
            ],
            'expanded' => true,
            'multiple' => false,
            'choice_attr' => function($choice, $key, $value) {
                return ['id' => 'commentaire_note_'.$value];
            },
            'choice_label' => function($choice, $key, $value) {
                return sprintf('☆', $value);
            },
        ]);
    }

    public function getParent()
    {
        return ChoiceType::class;
    }
}

?>