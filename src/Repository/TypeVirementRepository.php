<?php

namespace App\Repository;

use App\Entity\TypeVirement;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<TypeVirement>
 *
 * @method TypeVirement|null find($id, $lockMode = null, $lockVersion = null)
 * @method TypeVirement|null findOneBy(array $criteria, array $orderBy = null)
 * @method TypeVirement[]    findAll()
 * @method TypeVirement[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class TypeVirementRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, TypeVirement::class);
    }

//    /**
//     * @return TypeVirement[] Returns an array of TypeVirement objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('t')
//            ->andWhere('t.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('t.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?TypeVirement
//    {
//        return $this->createQueryBuilder('t')
//            ->andWhere('t.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
