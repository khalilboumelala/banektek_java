<?php

namespace App\Entity;

use App\Repository\CompteRepository;
use DateTime;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: CompteRepository::class)]
class Compte
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\ManyToOne(inversedBy: 'comptes')]
    private ?Client $id_user = null;

    #[ORM\ManyToOne(inversedBy: 'comptes')]
    private ?Agence $id_agence = null;

    #[ORM\Column(length: 255)]
    private ?string $type = null;

    #[ORM\Column]
    private ?float $solde = null;

    #[ORM\Column(type: Types::DATETIME_MUTABLE)]
    private ?\DateTimeInterface $date_creation = null;

    #[ORM\Column(type: Types::DATETIME_MUTABLE)]
    private ?\DateTimeInterface $date_fermeture = null;

    #[ORM\Column(length: 255)]
    private ?string $etat = null;
    #[ORM\Column(length : 255)]
    private ?string $rib = null;

    #[ORM\OneToMany(mappedBy: 'id_compte_emetteur', targetEntity: Virement::class)]
    private Collection $virements;

    #[ORM\OneToMany(mappedBy: 'id_compte_beneficiaire', targetEntity: Virement::class)]
    private Collection $virements_recus;

    #[ORM\OneToMany(mappedBy: 'id_compte', targetEntity: Transaction::class)]
    private Collection $transactions;

    #[ORM\OneToMany(mappedBy: 'id_compte', targetEntity: Credit::class)]
    private Collection $credits;

    public function __construct()
    {
        $this->virements = new ArrayCollection();
        $this->virements_recus = new ArrayCollection();
        $this->transactions = new ArrayCollection();
        $this->credits = new ArrayCollection();
        
        $datestring='0000-00-00';
        $date=new DateTime($datestring);
        $this->date_fermeture = $date ;
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdUser(): ?Client
    {
        return $this->id_user;
    }

    public function setIdUser(?Client $id_user): static
    {
        $this->id_user = $id_user;

        return $this;
    }

    public function getIdAgence(): ?Agence
    {
        return $this->id_agence;
    }

    public function setIdAgence(?Agence $id_agence): static
    {
        $this->id_agence = $id_agence;

        return $this;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): static
    {
        $this->type = $type;

        return $this;
    }

    public function getSolde(): ?float
    {
        return $this->solde;
    }

    public function setSolde(float $solde): static
    {
        $this->solde = $solde;

        return $this;
    }

    public function getDateCreation(): ?\DateTimeInterface
    {
        return $this->date_creation;
    }

    public function setDateCreation(\DateTimeInterface $date_creation): static
    {
        $this->date_creation = $date_creation;

        return $this;
    }

    public function getDateFermeture(): ?\DateTimeInterface
    {
        return $this->date_fermeture;
    }

    public function setDateFermeture(\DateTimeInterface $date_fermeture): static
    {
        $this->date_fermeture = $date_fermeture;

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

    public function getRib(): ?string
    {
        return $this->rib;
    }

    public function setRib(string $rib): static
    {
        $this->rib = $rib;

        return $this;
    }

    /**
     * @return Collection<int, Virement>
     */
    public function getVirements(): Collection
    {
        return $this->virements;
    }

    public function addVirement(Virement $virement): static
    {
        if (!$this->virements->contains($virement)) {
            $this->virements->add($virement);
            $virement->setIdCompteEmetteur($this);
        }

        return $this;
    }

    public function removeVirement(Virement $virement): static
    {
        if ($this->virements->removeElement($virement)) {
            // set the owning side to null (unless already changed)
            if ($virement->getIdCompteEmetteur() === $this) {
                $virement->setIdCompteEmetteur(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Virement>
     */
    public function getVirementsRecus(): Collection
    {
        return $this->virements_recus;
    }

    public function addVirementsRecu(Virement $virementsRecu): static
    {
        if (!$this->virements_recus->contains($virementsRecu)) {
            $this->virements_recus->add($virementsRecu);
            $virementsRecu->setIdCompteBeneficiaire($this);
        }

        return $this;
    }

    public function removeVirementsRecu(Virement $virementsRecu): static
    {
        if ($this->virements_recus->removeElement($virementsRecu)) {
            // set the owning side to null (unless already changed)
            if ($virementsRecu->getIdCompteBeneficiaire() === $this) {
                $virementsRecu->setIdCompteBeneficiaire(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Transaction>
     */
    public function getTransactions(): Collection
    {
        return $this->transactions;
    }

    public function addTransaction(Transaction $transaction): static
    {
        if (!$this->transactions->contains($transaction)) {
            $this->transactions->add($transaction);
            $transaction->setIdCompte($this);
        }

        return $this;
    }

    public function removeTransaction(Transaction $transaction): static
    {
        if ($this->transactions->removeElement($transaction)) {
            // set the owning side to null (unless already changed)
            if ($transaction->getIdCompte() === $this) {
                $transaction->setIdCompte(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Credit>
     */
    public function getCredits(): Collection
    {
        return $this->credits;
    }

    public function addCredit(Credit $credit): static
    {
        if (!$this->credits->contains($credit)) {
            $this->credits->add($credit);
            $credit->setIdCompte($this);
        }

        return $this;
    }

    public function removeCredit(Credit $credit): static
    {
        if ($this->credits->removeElement($credit)) {
            // set the owning side to null (unless already changed)
            if ($credit->getIdCompte() === $this) {
                $credit->setIdCompte(null);
            }
        }

        return $this;
    }

    
}
