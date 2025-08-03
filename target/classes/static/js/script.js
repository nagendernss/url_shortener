// src/main/resources/static/js/script.js
function copyToClipboard() {
    const shortUrl = document.getElementById('shortUrl').value;

    if (navigator.clipboard && window.isSecureContext) {
        // Use the Clipboard API
        navigator.clipboard.writeText(shortUrl).then(() => {
            showCopySuccess();
        }).catch(err => {
            console.error('Failed to copy: ', err);
            fallbackCopyTextToClipboard(shortUrl);
        });
    } else {
        // Fallback for older browsers
        fallbackCopyTextToClipboard(shortUrl);
    }
}

function fallbackCopyTextToClipboard(text) {
    const textArea = document.createElement("textarea");
    textArea.value = text;

    // Avoid scrolling to bottom
    textArea.style.top = "0";
    textArea.style.left = "0";
    textArea.style.position = "fixed";

    document.body.appendChild(textArea);
    textArea.focus();
    textArea.select();

    try {
        const successful = document.execCommand('copy');
        if (successful) {
            showCopySuccess();
        } else {
            console.error('Fallback: Copying text command was unsuccessful');
        }
    } catch (err) {
        console.error('Fallback: Oops, unable to copy', err);
    }

    document.body.removeChild(textArea);
}

function showCopySuccess() {
    const button = event.target;
    const originalText = button.textContent;

    button.textContent = 'Copied!';
    button.classList.add('btn-copied');

    setTimeout(() => {
        button.textContent = originalText;
        button.classList.remove('btn-copied');
    }, 2000);
}

// Form validation and enhancement
document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const submitButton = form?.querySelector('button[type="submit"]');

    if (form && submitButton) {
        form.addEventListener('submit', function() {
            submitButton.disabled = true;
            submitButton.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Shortening...';
        });
    }

    // Auto-focus on URL input
    const urlInput = document.getElementById('url');
    if (urlInput && !urlInput.value) {
        urlInput.focus();
    }
});

// AJAX form submission (optional enhancement)
function submitFormAjax() {
    const form = document.querySelector('form');
    const formData = new FormData(form);
    const data = Object.fromEntries(formData);

    fetch('/api/shorten', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                showError(data.error);
            } else {
                showSuccess(data);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showError('An error occurred while shortening the URL');
        });
}

function showError(message) {
    // Create or update error alert
    const alertDiv = document.createElement('div');
    alertDiv.className = 'alert alert-danger';
    alertDiv.textContent = message;

    const container = document.querySelector('.card-body');
    container.insertBefore(alertDiv, container.firstChild);
}

function showSuccess(data) {
    // Create success alert with shortened URL
    const alertDiv = document.createElement('div');
    alertDiv.className = 'alert alert-success';
    alertDiv.innerHTML = `
        <h5>URL Shortened Successfully!</h5>
        <p><strong>Short URL:</strong> <a href="${data.shortUrl}" target="_blank">${data.shortUrl}</a></p>
        <p><strong>Original URL:</strong> ${data.originalUrl}</p>
        <p><strong>Analytics:</strong> <a href="/analytics/${data.shortId}">View Analytics</a></p>
        <button class="btn btn-sm btn-outline-primary" onclick="copyUrlToClipboard('${data.shortUrl}')">Copy Short URL</button>
    `;

    const container = document.querySelector('.card-body');
    container.insertBefore(alertDiv, container.firstChild);
}

function copyUrlToClipboard(url) {
    if (navigator.clipboard && window.isSecureContext) {
        navigator.clipboard.writeText(url).then(() => {
            showCopySuccess();
        });
    } else {
        fallbackCopyTextToClipboard(url);
    }
}
