<?php

namespace App\Controller;

use App\Entity\Agent;
use App\Entity\Client;
use App\Repository\ClientRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;

class LoginController extends AbstractController
{
   
    #[Route('/login', name: 'app_login')]
    public function Login_check_client(Request $requesty): Response
    {
        $username = $requesty->request->get('username');
        $password = $requesty->request->get('password');
        $client = $this->getDoctrine()->getRepository(Client::class)->findOneBy(['username' => $username]);
        if ($client) {
            if (password_verify($password,$client->getPassword())) {
                $session = $requesty->getSession();
                $session->set('username', $username);
                $session->set('id', $client->getId());
                return $this->redirectToRoute('app_my_card');
            } else {
                return $this->redirectToRoute('app_article_index');
            }
        } else {
            return $this->redirectToRoute('app_article_index');
        }
    }

    #[Route('/faceid', name: 'face_id')]
    public function faceid(SessionInterface $session): Response
    {
        return $this->render('login/faceid.html.twig', [
          
        ]);
    }

    #[Route('/logout', name: 'app_logout')]
    public function logout(SessionInterface $session): Response
    {
        $session->clear();
        return $this->redirectToRoute('app_article_index');
    }
    ///////////////////////////////////////////:
    #[Route('/loginadmin', name: 'app_login_admin')]
    public function Login_check_admin(Request $requesty): Response
    {
        $matricule = $requesty->request->get('matricule');
        $password = $requesty->request->get('password');
        $agent = $this->getDoctrine()->getRepository(Agent::class)->findOneBy(['matricule' => $matricule]);
        if ($agent) {
            if (password_verify($password,$agent->getPassword())){
                $session = $requesty->getSession();
                $session->set('matrricule', $matricule);
                $session->set('id_agent', $agent->getId());
                return $this->redirectToRoute('app_agent_new');
            } else {
                return $this->redirectToRoute('app_agent_login');
            }
        } else {
            return $this->redirectToRoute('app_agent_login');
        }
    }
    
    ///////////////////////////////////////////:
    #[Route('/loginadminfaceid/{faceid}', name: 'app_login_admin_faceid')]
    public function Login_check_admin_faceid(Request $requesty,$faceid): Response
    {
      //  $faceid= $requesty->request->get('agent_faceid');
      
        $agent = $this->getDoctrine()->getRepository(Agent::class)->findOneBy(['faceid' => $faceid]);
        if ($agent) {
            $session = $requesty->getSession();
            $session->set('matrricule', $agent->getMatricule());
            $session->set('id_agent', $agent->getId());
                return $this->redirectToRoute('app_agent_new');
            } else {
                return $this->redirectToRoute('app_agent_login');
            }
        }

}
