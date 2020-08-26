# Visulog

# Visulog

*Outil de visualisaton et d'analyse de journal de git*

Visulog est un logiciel d'analyse des contributions des différents membres d'une équipe travaillant sur un même projet versionné sur un dépôt git. Son but est d'aider les enseignants à évaluer de façon individuelle un projet réalisé en équipe.

Ce logiciel permet de :

- calculer de quelques indicateurs quantitatifs adéquats, comme :
  - lignes ou caractères ajoutés/supprimés/modifiés par chaque membre de l'équipe
  - nombre de commits
  - nombre de merges (provenant de MR)
- analyser ces indicateurs en fonction du temps (par exemple, total par semaine, moyenne journalière, moyenne glissante, etc.)
- visualiser de ces indicateurs sous forme de graphiques (histogrammes, camemberts, etc.), intégrés à une page web générée par ce logiciel.

Les graphiques sont générés par une bibliothèque tierce (soit sous forme d'images, par une bibliothèque java, soit dynamiquement dans la page web, par une bibliothèque javascript).