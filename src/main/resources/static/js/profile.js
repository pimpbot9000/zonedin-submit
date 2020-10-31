/**
*   Handle deleting contacts, cancelling invites, accepting invites and rejecting invites
*   in other users profile. A bit lazy implementation which reloads the page when result
*   is received. Initial meaning was to make page dynamic with out reloading thw whole
*   page but the damn project had a deadline so corners had to be cut.
*/

$(function () {

    $('#sendInviteForm').on('submit', (event) => {
        event.preventDefault();
        console.log(event.target.action);

        axios.post(event.target.action)
            .then(function (response) {                
                location.reload();
            })
            .catch(function (error) {
                // handle error
                console.log(error);
            })
    });
    
    $('#removeContactForm').on('submit', (event) => {
        event.preventDefault();
        console.log(event.target.action);

        axios.delete(event.target.action)
            .then(function (response) {
                
                $('#removeContact').addClass('d-none')
            })
            .catch(function (error) {
                // handle error
                console.log(error);
            })
    });
    
    $('#cancelPendingInviteForm').on('submit', (event) => {
        event.preventDefault();
        console.log(event.target.action);

        axios.delete(event.target.action)
            .then(function (response) {
               location.reload();
            })
            .catch(function (error) {
                // handle error
                console.log(error);
            })
    });

    $('#dismissPendingApprovalForm').on('submit', (event) => {
        event.preventDefault();
        console.log(event.target.action);

        axios.delete(event.target.action)
            .then(function (response) {
                location.reload();
            })
            .catch(function (error) {
                // handle error
                console.log(error);
            })
    });

    $('#acceptPendingApprovalForm').on('submit', (event) => {
        event.preventDefault();
        console.log(event.target.action);

        axios.post(event.target.action)
            .then(function (response) {
                location.reload();
            })
            .catch( (error) => {
                location.reload(); 
            })
    });
});

