<?php

namespace App\Controller;
//include_once '../src/Entity/Article.php';
use App\Entity\Article;
use App\Entity\Client;
use App\Entity\Commentaire;
use App\Form\ArticleType;
use App\Form\CommentaireType;
use App\Repository\ArticleRepository;
use App\Repository\CommentaireRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;

    


#[Route('/article')]
class ArticleController extends AbstractController
{
    #[Route('/', name: 'app_article_index', methods: ['GET'])]
    public function index(ArticleRepository $articleRepository): Response
    {
       
       $articles= $articleRepository->findAll();
      
        return $this->render('article/index.html.twig', [
            'articles' => $articles,
         
        ]);
    }
    #[Route('/index', name: 'app_article_index2', methods: ['GET'])]
    public function indexback(ArticleRepository $articleRepository): Response
    {
        return $this->render('article/convertisseur.html.twig', [
            'articles' => $articleRepository->findAll(),
        ]);
    }

    #[Route('/convertisseur', name: 'app_convertisseur', methods: ['GET'])]
    public function convertir(ArticleRepository $articleRepository): Response
    {

        
        return $this->render('article/convertisseur.html.twig',[
            'articles' =>$articleRepository->findAll(),
      
        ]);
    }
   
   
    #[Route('/new', name: 'app_article_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $article = new Article();
        
        $form = $this->createForm(ArticleType::class, $article);
        $article->setDatePub(( new \DateTime()));
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
           
            $entityManager->flush();
    
            // Now that the article is persisted, get its ID
            $articleId = $article->getId();
            $articletitle = $article->getTitre();
            $tempFilePath = $form['image']->getData();
            $destinationPath = "uploads/" .$articletitle.$articleId.".png";
            $compressionQuality = 100;
    
            $this->compressImage($tempFilePath, $destinationPath, $compressionQuality);
    
            $article->setImage($destinationPath);
            $entityManager->persist($article);
            $entityManager->flush();
    
            return $this->redirectToRoute('app_article_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('article/new.html.twig', [
            'article' => $article,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_article_show', methods: ['GET', 'POST'])]
    public function show(Article $article, CommentaireRepository $commentaireRepository, EntityManagerInterface $entityManager, Request $request): Response
    {
           // Get commentaires
      
        $commentaires = $commentaireRepository->findBy(['article' => $article->getId()]);
        // Create form
        $commentaire = new Commentaire();
      //  $commentairecontroller = new CommentaireController();
        $formCommentaire = $this->createForm(CommentaireType::class, $commentaire);
        $formCommentaire->handleRequest($request);
      
     

        if ($formCommentaire->isSubmitted() && $formCommentaire->isValid()) {
        
            //$entityManager->flush();
            $commentaire->setDate(new \DateTime());
            $commentaire->setArticle($article);
            $session = $request->getSession();
            $client = $this->getDoctrine()->getRepository(Client::class)->findOneBy(['username' =>  $session->get('username')]);
            $commentaire->setUser($client);
            $entityManager->persist($commentaire);
            $entityManager->flush();
        
            return $this->redirectToRoute('app_article_show', ['id' => 1], Response::HTTP_SEE_OTHER);
        }
        return $this->renderForm('article/show.html.twig', [
            'article' => $article,
            'commentaires' => $commentaires,
            'commentform' => $formCommentaire,
        ]);
    }
    

    #[Route('/{id}/edit', name: 'app_article_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Article $article, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(ArticleType::class, $article);
        
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_article_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('article/edit.html.twig', [
            'article' => $article,
            'form' => $form,
        ]);
    }

    #[Route('/delete/{id}', name: 'app_article_delete', methods: ['POST'])]
    public function delete(Request $request, Article $article, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$article->getId(), $request->request->get('_token'))) {
            $entityManager->remove($article);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_article_index', [], Response::HTTP_SEE_OTHER);
    }

    



    

 private  function compressImage($source, $destination, $quality) {
        $info = getimagesize($source);
        
        if ($info['mime'] == 'image/jpeg') {
            $image = imagecreatefromjpeg($source);
        } elseif ($info['mime'] == 'image/png') {
            $image = imagecreatefrompng($source);
        } elseif ($info['mime'] == 'image/gif') {
            $image = imagecreatefromgif($source);
        } else {
            return false;
        }    // Sauvegarder l'image compressée
           imagejpeg($image, $destination, $quality);
           
           // Libérer la mémoire
           imagedestroy($image);
           
           return true;
       }
}
