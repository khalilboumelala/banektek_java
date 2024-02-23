document.addEventListener('DOMContentLoaded', function() {
 var $type = document.getElementById('credit_type');
 var $montant = document.getElementById('credit_montant');
 var $taux = document.getElementById('credit_taux');
 var $duree = document.getElementById('credit_duree');
 var $motant_echeance = document.getElementById('credit_montant_echeance');
 var $apport_propre = document.getElementById('credit_apport_propre');
    $apport_propre.classList.add
 function calculer_taux() {
     if ($type.value === 'commercial') {
         $taux.value = 0.09;
     } else if ($type.value === 'agricole') {
         $taux.value = 0.07;
     } else if ($type.value === 'automobile') {
         $taux.value = 0.1;
     } else if ($type.value === 'consommation') {
         $taux.value = 0.12;
     } else if ($type.value === 'hypothecaire') {
         $taux.value = 0.05;
     } else if ($type.value === 'etudiant') {
         $taux.value = 0.11;
     }
 }

 function calculer_motant_echeance() {
     var montant_credit = $montant.value - $apport_propre.value;
    $motant_echeance.value = ((montant_credit * $taux.value + montant_credit) / $duree.value).toFixed(3);
 }

 calculer_taux();
 calculer_motant_echeance();

 $type.addEventListener('change', function() {
     calculer_taux();
     calculer_motant_echeance();
 });
 
 $montant.addEventListener('change', function() {
  calculer_taux();
  calculer_motant_echeance();
});
$duree.addEventListener('change', function() {
 calculer_taux();
 calculer_motant_echeance();
});
$apport_propre.addEventListener('change', function() {
 calculer_taux();
 calculer_motant_echeance();
});

} );
