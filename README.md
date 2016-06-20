# Set Up Git
**[Source Address](https://help.github.com/articles/set-up-git/#setting-up-git)**

## Setting up Git
**1**:   [Download and install the latest version of Git.](https://git-scm.com/downloads)

**2**:   On your computer, open the **Terminal** application.

**3**:   Tell Git your name so your commits will be properly labeled. Type everything after the $ here:

>         $ git config --global user.name "YOUR NAME"
        
**4**:   Tell Git the email address that will be associated with your Git commits. The email you specify should be the same one found in your [email settings](https://help.github.com/articles/adding-an-email-address-to-your-github-account/). To keep your email address hidden, see "[Keeping your email address private](https://help.github.com/articles/keeping-your-email-address-private)".

>         $ git config --global user.email "YOUR EMAIL ADDRESS"

## Authenticating with GitHub from Git
When you connect to a GitHub repository from Git, you'll need to authenticate with GitHub using either HTTPS or SSH.

### Connecting over HTTPS (recommended)

If you [clone with HTTPS](https://help.github.com/articles/which-remote-url-should-i-use/#cloning-with-https-urls-recommended), you can [cache your GitHub password in Git](https://help.github.com/articles/caching-your-github-password-in-git) using a credential helper.

### Connecting over SSH

If you [clone with SSH](https://help.github.com/articles/which-remote-url-should-i-use#cloning-with-ssh-urls), you must [generate SSH keys](https://help.github.com/articles/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent) on each computer you use to push or pull from GitHub.

  
