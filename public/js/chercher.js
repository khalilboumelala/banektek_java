// Attendez que le DOM soit chargé
document.addEventListener('DOMContentLoaded', function() {
    console.log("aaaa");

    // Ajoutez un gestionnaire d'événements au champ de recherche
    var searchInput = document.getElementById('searchInput');
    if (searchInput) {
        searchInput.addEventListener('input', function() {
            var searchValue = this.value.toLowerCase();

            // Loop through each table row and hide/show based on the search value
            var rows = document.querySelectorAll('#clients-table tbody tr');

            for (var i = 0; i < rows.length; i++) {
                var name = rows[i].textContent.toLowerCase();

                if (name.includes(searchValue)) {
                    rows[i].style.display = ''; // Show the row if the name matches the search value
                } else {
                    rows[i].style.display = 'none'; // Hide the row if the name does not match the search value
                }
            }
        });
    }
});
