

var faceidInput = document.getElementById("agent_faceid");

// Ajouter un gestionnaire d'événements onchange
faceidInput.addEventListener("change", function() {
    // Rediriger vers la route "login"
   console.log(faceidInput.value);
   // window.location.href = "/login"; // Remplacez "/login" par le chemin de votre route login
});