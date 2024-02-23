<?php 
// src/Controller/MailerController.php
namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\Exception\TransportExceptionInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mailer\Transport;
use Symfony\Component\Mime\Email;
use Symfony\Component\Routing\Annotation\Route;

class MailerController extends AbstractController
{
    #[Route('/mail')]
    public function sendEmail(MailerInterface $mailer): Response
    {  
        $email = (new Email())
            ->from('hamzanasr883@gmail.com')
            ->to('nasr.hamza@esprit.tn')
            ->subject('Time for Symfony Mailer!')
            ->text('Sending emails is fun again!')
            ->html('<p>See Twig integration for better HTML integration!</p>');
    
        try {
            $mailer->send($email);
            $message = 'Email sent successfully!';
        } catch (TransportExceptionInterface $e) {
            // Gérer les erreurs d'envoi de l'e-mail
            $message = 'Failed to send email: ' . $e->getMessage();
        }
    
        return $this->render('agent/mail.html.twig', [
            'mailer' => $mailer,
            'message' => $message,
        ]);
    }
}