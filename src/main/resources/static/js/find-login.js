	// 수정 필요
    // Tab switching functionality
    document.getElementById('findIdTab').addEventListener('click', function() {
        switchTab('tabContentFindId', 'findIdTab');
    });

    document.getElementById('findPwTab').addEventListener('click', function() {
        switchTab('tabContentFindPw', 'findPwTab');
    });

    function switchTab(contentId, tabId) {
        // Hide all tab contents
        document.querySelectorAll('.tab--content').forEach(function(content) {
            content.classList.remove('active');
        });
        // Remove active class from all tabs
        document.querySelectorAll('.tab--item').forEach(function(tab) {
            tab.classList.remove('active');
        });
        // Show selected tab content and add active class to the clicked tab
        document.getElementById(contentId).classList.add('active');
        document.getElementById(tabId).classList.add('active');
    }