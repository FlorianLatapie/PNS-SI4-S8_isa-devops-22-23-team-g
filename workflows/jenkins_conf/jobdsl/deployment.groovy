multibranchPipelineJob('deployment') {
  displayName('deployment')
  branchSources {
      branchSource {
        source {
          github {
            id('deployment')
            repository('isa-devops-22-23-team-g-23.git')
            repoOwner('pns-isa-devops')
            credentialsId('FlorianGitHub')
            repositoryUrl('https://github.com/pns-isa-devops/isa-devops-22-23-team-g-23.git')
            configuredByUrl(true)
            traits {
              gitHubBranchDiscovery{
                strategyId(1)
              }
              gitHubPullRequestDiscovery{
                strategyId(2)
              }
              notificationContextTrait {
                contextLabel('continuous-integration/jenkins')
                typeSuffix(true)
              }
              headRegexFilterWithPRFromOrigin{
                regex('^main$')
                tagRegex('^[0-9]+\\.[0-9]+\\.[0-9]+$')
              }
              gitHubIgnoreDraftPullRequestFilter()
            }
          }
        }
      }
    }
  orphanedItemStrategy {
      discardOldItems {
          numToKeep(5)
      }
  }
  configure {
      def traits = it / 'sources' / 'data' / 'jenkins.branch.BranchSource' / 'source' / 'traits'
      traits << 'org.jenkinsci.plugins.github__branch__source.ForkPullRequestDiscoveryTrait' {
          strategyId(2)
          trust(class: 'org.jenkinsci.plugins.github_branch_source.ForkPullRequestDiscoveryTrait$TrustPermission')
      }
  }
  factory {
      workflowBranchProjectFactory {
          scriptPath('workflows/deployment.Jenkinsfile')
      }
  }
}