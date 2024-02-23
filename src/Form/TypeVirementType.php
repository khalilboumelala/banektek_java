<?php

namespace App\Form;

use App\Entity\TypeVirement;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class TypeVirementType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom')   
            ->add('frais', ChoiceType::class, [
                'label' => 'Frais',
                'choices' => $this->createFraisChoices(),
                'placeholder' => 'Choisir les frais',
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => TypeVirement::class,
        ]);
    }
    
    private function createFraisChoices(): array
    {
        $choices = [];
        foreach (range(5, 55, 5) as $value) {
            $label = $value . ' DT';
            $choices[$label] = $value; // Use the value itself as the choice
        }

        return $choices;
    }
}
