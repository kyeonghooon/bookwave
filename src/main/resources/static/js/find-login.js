	// 수정 필요
    // Tab
    document.getElementById('findIdTab').addEventListener('click', function() {
        switchTab('tabContentFindId', 'findIdTab');
    });

    document.getElementById('findPwTab').addEventListener('click', function() {
        switchTab('tabContentFindPw', 'findPwTab');
    });

    function switchTab(contentId, tabId) {
    
        document.querySelectorAll('.tab--content').forEach(function(content) {
            content.classList.remove('active');
        });
  
        document.querySelectorAll('.tab--item').forEach(function(tab) {
            tab.classList.remove('active');
        });
     
        document.getElementById(contentId).classList.add('active');
        document.getElementById(tabId).classList.add('active');
    }
    
    if (type == "id"){
		switchTab('tabContentFindId', 'findIdTab');
	} else {
		switchTab('tabContentFindPw', 'findPwTab');
	}