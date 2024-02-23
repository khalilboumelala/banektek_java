<?php

namespace App\Service;

use Twilio\Rest\Client;
class TwilioService
{
    public function sendVoiceOTP($recipientPhoneNumber, $otpCode)
    {
        $accountSid = $_ENV['TWILIO_ACCOUNT_SID'];
        $authToken = $_ENV['TWILIO_AUTH_TOKEN'];
        $twilioPhoneNumber = $_ENV['TWILIO_PHONE_NUMBER'];
        $client = new Client($accountSid, $authToken);
        $call = $client->calls->create(
            $recipientPhoneNumber,
            $twilioPhoneNumber, 
            [
                'twiml' => '<Response>
                <Say>
                    welcome to banektek , your verficiation code is '.$otpCode.', one more time your verification code is '.$otpCode.'
                </Say>
              </Response>'
              ]
        );
        return $call->sid;
    }
}