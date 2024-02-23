<?php

namespace App\Entity;

use App\Repository\VirementRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: VirementRepository::class)]
class Virement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\ManyToOne(inversedBy: 'virements')]
    private ?Compte $id_compte_emetteur = null;

    #[ORM\ManyToOne(inversedBy: 'virements_recus')]
    private ?Compte $id_compte_beneficiaire = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $cin_emetteur = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $photo_cin = null;

    #[ORM\Column]
    private ?float $montant = null;

    #[ORM\Column(type: Types::DATETIME_MUTABLE)]
    private ?\DateTimeInterface $date_emission = null;

    #[ORM\Column(type: Types::DATETIME_MUTABLE)]
    private ?\DateTimeInterface $date_approbation = null;

    #[ORM\Column(length: 255)]
    private ?string $etat = null;

    #[ORM\ManyToOne(inversedBy: 'virements')]
    private ?TypeVirement $type = null;

    public function __construct()
    {
        $this->date_emission = new \DateTime();
        $this->etat = "Succès";
    }
    
    public function getType(): ?TypeVirement
    {
        return $this->type;
    }
    public function setType(?TypeVirement $type): static
    {
        $this->type = $type;

        return $this;
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdCompteEmetteur(): ?Compte
    {
        return $this->id_compte_emetteur;
    }

    public function setIdCompteEmetteur(?Compte $id_compte_emetteur): static
    {
        $this->id_compte_emetteur = $id_compte_emetteur;

        return $this;
    }

    public function getIdCompteBeneficiaire(): ?Compte
    {
        return $this->id_compte_beneficiaire;
    }

    public function setIdCompteBeneficiaire(?Compte $id_compte_beneficiaire): static
    {
        $this->id_compte_beneficiaire = $id_compte_beneficiaire;

        return $this;
    }

    public function getCinEmetteur(): ?string
    {
        return $this->cin_emetteur;
    }

    public function setCinEmetteur(?string $cin_emetteur): static
    {
        $this->cin_emetteur = $cin_emetteur;

        return $this;
    }

    public function getPhotoCin(): ?string
    {
        return $this->photo_cin;
    }

    public function setPhotoCin(?string $photo_cin): static
    {
        $this->photo_cin = $photo_cin;

        return $this;
    }

    public function getMontant(): ?float
    {
        return $this->montant;
    }

    public function setMontant(float $montant): static
    {
        $this->montant = $montant;

        return $this;
    }

    public function getDateEmission(): ?\DateTimeInterface
    {
        return $this->date_emission;
    }

    public function setDateEmission(\DateTimeInterface $date_emission): static
    {
        $this->date_emission = $date_emission;

        return $this;
    }

    public function getDateApprobation(): ?\DateTimeInterface
    {
        return $this->date_approbation;
    }

    public function setDateApprobation(\DateTimeInterface $date_approbation): static
    {
        $this->date_approbation = $date_approbation;

        return $this;
    }

    public function getEtat(): ?string
    {
        return $this->etat;
    }

    public function setEtat(string $etat): static
    {
        $this->etat = $etat;

        return $this;
    }


   
}
