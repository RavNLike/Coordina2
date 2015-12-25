# coordina2

#Organització

1) Hem decididit externalitzar les classes entitat de la capa lógica (BLL) fins al paquet POJO

2) Hem utilitzat paquets en profunditat per a tractar d'ornedar el contingut de l'aplicació

3) La classe BLL.Coordina2 es el controlador de la capa lógic, el verdader muscul de l'aplicació, totes les crides realitzades desde la presentació al BLL pasen per ací.

4) S'ha decidit prescindir del controlador de la capa DAO, que encara que es necessari com a punt de crida únic quan distints desenvolupadors escriuen l'aplicació, pel tamany reduït d'aquesta em cregüt que portaría més complicacions que solucions.

#Coses a fer

1) Hi ha que estar atent amb el detall de que els tutelats tinguen que tindre un grup assignat -> possible problema en el volcat de dades incial