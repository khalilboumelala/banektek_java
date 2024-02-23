<?php

namespace App\Entity;

use App\Repository\ReponseRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: ReponseRepository::class)]
class Reponse
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\ManyToOne(inversedBy: 'reponses')]
    private ?Reclamation $id_reclamation = null;

    #[ORM\Column(type: Types::DATETIME_MUTABLE)]
    private ?\DateTimeInterface $date_reponse = null;

    #[ORM\ManyToOne(inversedBy: 'reponses')]
    private ?Agent $id_agent = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdReclamation(): ?Reclamation
    {
        return $this->id_reclamation;
    }

    public function setIdReclamation(?Reclamation $id_reclamation): static
    {
        $this->id_reclamation = $id_reclamation;

        return $this;
    }

    public function getDateReponse(): ?\DateTimeInterface
    {
        return $this->date_reponse;
    }

    public function setDateReponse(\DateTimeInterface $date_reponse): static
    {
        $this->date_reponse = $date_reponse;

        return $this;
    }

    public function getIdAgent(): ?Agent
    {
        return $this->id_agent;
    }

    public function setIdAgent(?Agent $id_agent): static
    {
        $this->id_agent = $id_agent;

        return $this;
    }
}
