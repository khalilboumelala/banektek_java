<?php

namespace App\Entity;

use App\Entity\Echeance;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\CreditRepository;
use Doctrine\Common\Collections\Collection;
use Doctrine\Common\Collections\ArrayCollection;

#[ORM\Entity(repositoryClass: CreditRepository::class)]
class Credit
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\ManyToOne(inversedBy: 'credits')]
    private ?Compte $id_compte = null;

    #[ORM\Column]
    private ?float $montant = null;

    #[ORM\Column]
    private ?float $taux = null;

    #[ORM\Column]
    private ?int $duree = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $date_debut = null;

    #[ORM\Column(length: 255)]
    private ?string $type = null;

    #[ORM\Column]
    private ?float $apport_propre = null;

    #[ORM\Column]
    private ?float $revenu_propre = null;

    #[ORM\Column]
    private ?float $montant_echeance = null;

    #[ORM\Column]
    private ?int $date_echeance = null;

    #[ORM\Column(length: 255)]
    private ?string $etat = null;

    #[ORM\Column]
    private ?int $nb_echeances_restants = null;

    #[ORM\OneToMany(mappedBy: 'id_credit', targetEntity: Echeance::class)]
    private Collection $echeances;


    public function __construct()
    {
        $this->echeances = new ArrayCollection();
      
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdCompte(): ?Compte
    {
        return $this->id_compte;
    }

    public function setIdCompte(?Compte $id_compte): static
    {
        $this->id_compte = $id_compte;

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

    public function getTaux(): ?float
    {
        return $this->taux;
    }

    public function setTaux(float $taux): static
    {
        $this->taux = $taux;

        return $this;
    }

    public function getDuree(): ?int
    {
        return $this->duree;
    }

    public function setDuree(int $duree): static
    {
        $this->duree = $duree;

        return $this;
    }

    public function getDateDebut(): ?\DateTimeInterface
    {
        return $this->date_debut;
    }

    public function setDateDebut(\DateTimeInterface $date_debut): static
    {
        $this->date_debut = $date_debut;

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

    public function getApportPropre(): ?float
    {
        return $this->apport_propre;
    }

    public function setApportPropre(float $apport_propre): static
    {
        $this->apport_propre = $apport_propre;

        return $this;
    }

    public function getRevenuPropre(): ?float
    {
        return $this->revenu_propre;
    }

    public function setRevenuPropre(float $revenu_propre): static
    {
        $this->revenu_propre = $revenu_propre;

        return $this;
    }

    public function getMontantEcheance(): ?float
    {
        return $this->montant_echeance;
    }

    public function setMontantEcheance(float $montant_echeance): static
    {
        $this->montant_echeance = $montant_echeance;

        return $this;
    }

    public function getDateEcheance(): ?int
    {
        return $this->date_echeance;
    }

    public function setDateEcheance(int $date_echeance): static
    {
        $this->date_echeance = $date_echeance;

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

    public function getNbEcheancesRestants(): ?int
    {
        return $this->nb_echeances_restants;
    }

    public function setNbEcheancesRestants(int $nb_echeances_restants): static
    {
        $this->nb_echeances_restants = $nb_echeances_restants;

        return $this;
    }

    /**
     * @return Collection<int, Echeance>
     */
    public function getEcheances(): Collection
    {
        return $this->echeances;
    }

    public function addEcheance(Echeance $echeance): static
    {
        if (!$this->echeances->contains($echeance)) {
            $this->echeances->add($echeance);
            $echeance->setIdCredit($this);
        }

        return $this;
    }


    public function setEcheances(ArrayCollection $echeances): static
    {
       $this->echeances=$echeances;

        return $this;
    }

    public function removeEcheance(Echeance $echeance): static
    {
        if ($this->echeances->removeElement($echeance)) {
            // set the owning side to null (unless already changed)
            if ($echeance->getIdCredit() === $this) {
                $echeance->setIdCredit(null);
            }
        }

        return $this;
    }
}
