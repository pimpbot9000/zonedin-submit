/**
*   Handle deleting contacts, canceling invites, accepting invites and rejecting invites
*   using ajax in Home view.
*/

$(function () {
    $('.removeContactForm').on('submit', (event) => {
        event.preventDefault();
        console.log(event.target.action);

        axios.delete(event.target.action)
            .then(function (response) {
                console.log('#contact' + response.data.id);
                $('#contact' + response.data.id).remove();
            })
            .catch(function (error) {
                // handle error
                console.log(error);
            })
    });

    $('.cancelPendingInviteForm').on('submit', (event) => {
        event.preventDefault();
        console.log(event.target.action);

        axios.delete(event.target.action)
            .then(function (response) {
                console.log('#pendingInvite' + response.data.id);
                $('#pendingInvite' + response.data.id).remove();
            })
            .catch(function (error) {
                // handle error
                console.log(error);
            })
    });

    $('.dismissPendingApprovalForm').on('submit', (event) => {
        event.preventDefault();
        console.log(event.target.action);

        axios.delete(event.target.action)
            .then(function (response) {
                console.log('#pendingApproval' + response.data.id);
                $('#pendingApproval' + response.data.id).remove();
            })
            .catch(function (error) {
                // handle error
                console.log(error);
            })
    });

    $('.acceptPendingApprovalForm').on('submit', (event) => {
        event.preventDefault();
        console.log(event.target.action);

        axios.post(event.target.action)
            .then(function (response) {
                console.log(response)
                console.log('#pendingApproval' + response.data.id);

                // remove pending approval from list
                $('#pendingApproval' + response.data.id).remove();

                // show's the hidden contact ... bit crappy implementation, but if life gives you lemonades
                if (response.status === 200) {
                    $('#contact' + response.data.id).removeClass('d-none');
                } 
            })
            .catch( (error) => {                

                if( error.response.status === 410) {  // resource gone

                   // remove pending approval from list
                   $('#pendingApproval' + error.response.data.id).remove();

                   // while at it remove the hidden contact
                   $('#contact' + error.response.data.id).remove();

                }
 
            })
    });
});
