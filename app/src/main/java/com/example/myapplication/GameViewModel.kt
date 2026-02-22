package com.example.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    var gameConfig by mutableStateOf(GameConfig())
        private set
    
    var players by mutableStateOf<List<Player>>(emptyList())
        private set
    
    var currentPlayerIndex by mutableStateOf(0)
        private set
    
    var isGameStarted by mutableStateOf(false)
        private set
    
    var showVotingScreen by mutableStateOf(false)
        private set
    
    var votes by mutableStateOf<Map<Int, Int>>(emptyMap())
        private set
    
    // Palabras para el juego según el tema
    private val themeWords = mapOf(
        MusicTheme.MUSIC to listOf(
            // Rap Chileno
            "Bubaseta", "Chystemc", "Portavoz", "Jonas Sanche", "Jota Mayúscula",
            "Camilo Masilla", "Drefquila", "Pablo Chill-E", "Polimá Westcoast", "Harry Nach",
            // Rap Internacional
            "Eminem", "Tupac", "Kendrick Lamar", "Drake", "J Cole",
            "Canserbero", "Residente", "Nicki Jam", "Bad Bunny", "Daddy Yankee",
            // Pop/Rock
            "Michael Jackson", "Queen", "Beatles", "Coldplay", "Imagine Dragons",
            "Bruno Mars", "Ed Sheeran", "Taylor Swift", "Ariana Grande", "The Weeknd",
            // Reggaeton/Latino
            "Ozuna", "Maluma", "Shakira", "Karol G", "J Balvin",
            // Canciones famosas
            "Bohemian Rhapsody", "Thriller", "Despacito", "Shape of You", "Blinding Lights",
            "Hotel California", "Stairway to Heaven", "Smells Like Teen Spirit"
        ),
        MusicTheme.CHARACTERS to listOf(
            // Marvel
            "Spider-Man", "Iron Man", "Capitán América", "Thor", "Hulk",
            "Viuda Negra", "Doctor Strange", "Black Panther", "Wolverine", "Deadpool",
            "Thanos", "Loki", "Scarlet Witch", "Venom", "Groot",
            // DC
            "Batman", "Superman", "Wonder Woman", "Flash", "Aquaman",
            "Joker", "Harley Quinn", "Cyborg", "Green Lantern", "Lex Luthor",
            // Harry Potter
            "Harry Potter", "Hermione Granger", "Ron Weasley", "Dumbledore", "Voldemort",
            "Severus Snape", "Hagrid", "Draco Malfoy", "Sirius Black", "Dobby",
            // Actores famosos
            "Leonardo DiCaprio", "Tom Hanks", "Denzel Washington", "Morgan Freeman", "Robert Downey Jr",
            "Keanu Reeves", "Will Smith", "Brad Pitt", "Johnny Depp", "Al Pacino",
            // Personajes históricos
            "Albert Einstein", "Nikola Tesla", "Steve Jobs", "Pablo Escobar", "Che Guevara",
            "Nelson Mandela", "Martin Luther King", "Gandhi", "Cleopatra", "Napoleón Bonaparte",
            // Otros
            "Augusto Pinochet", "Salvador Allende", "Arturo Vidal", "Alexis Sánchez", "Gabriela Mistral"
        ),
        MusicTheme.MOVIES to listOf(
            // Acción
            "John Wick", "Matrix", "Die Hard", "Mad Max", "Terminator",
            "Rambo", "Jason Bourne", "Mission Impossible", "Fast and Furious", "The Equalizer",
            // Aventura
            "Indiana Jones", "Piratas del Caribe", "Jurassic Park", "Avatar", "El Señor de los Anillos",
            "Star Wars", "Gladiador", "Braveheart", "Troya", "300",
            // Superhéroes
            "Avengers", "The Dark Knight", "Man of Steel", "Guardianes de la Galaxia", "Logan",
            "Black Panther", "Spider-Man No Way Home", "Deadpool", "Joker", "Wonder Woman",
            // Clásicos
            "El Padrino", "Titanic", "Forrest Gump", "Pulp Fiction", "Shawshank Redemption",
            "El Club de la Pelea", "Inception", "Interstellar", "El Origen", "Parasite",
            // Terror/Suspenso
            "El Conjuro", "El Exorcista", "Halloween", "El Silencio de los Inocentes", "Psicosis",
            "It", "El Resplandor", "Scream", "Saw", "Hereditary",
            // Comedia
            "Mi Pobre Angelito", "Scary Movie", "Superbad", "The Hangover", "Ted"
        ),
        MusicTheme.GAMES to listOf(
            // Acción/Aventura
            "God of War", "The Last of Us", "Uncharted", "Red Dead Redemption", "GTA",
            "Assassins Creed", "Batman Arkham", "Tomb Raider", "Horizon Zero Dawn", "Ghost of Tsushima",
            // Shooter
            "Call of Duty", "Battlefield", "Halo", "Destiny", "Counter Strike",
            "Valorant", "Overwatch", "Apex Legends", "Fortnite", "PUBG",
            // RPG
            "The Witcher", "Skyrim", "Dark Souls", "Elden Ring", "Final Fantasy",
            "Dragon Age", "Mass Effect", "Cyberpunk 2077", "Persona", "Bloodborne",
            // Deportes
            "FIFA", "NBA 2K", "Madden NFL", "WWE 2K", "Rocket League",
            // Survival/Horror
            "Resident Evil", "Silent Hill", "Dead Space", "The Evil Within", "Outlast",
            // Plataformas/Aventura
            "Super Mario", "Zelda", "Crash Bandicoot", "Sonic", "Spyro",
            "Ratchet and Clank", "Sackboy", "Astro Bot", "Donkey Kong", "Kirby",
            // Online/Competitivo
            "League of Legends", "Dota 2", "Minecraft", "Roblox", "Among Us",
            "Fall Guys", "Fortnite", "World of Warcraft", "Genshin Impact", "Warzone"
        ),
        MusicTheme.ANIME to listOf(
            // Shonen populares
            "Naruto", "One Piece", "Bleach", "Dragon Ball", "My Hero Academia",
            "Demon Slayer", "Kimetsu no Yaiba", "Jujutsu Kaisen", "Chainsaw Man", "Black Clover",
            "One Punch Man", "Mob Psycho", "Hunter x Hunter", "Fullmetal Alchemist", "Attack on Titan",
            // Seinen
            "Berserk", "Tokyo Ghoul", "Parasyte", "Death Note", "Code Geass",
            "Steins Gate", "Cowboy Bebop", "Samurai Champloo", "Vinland Saga", "Monster",
            // Deportes
            "Haikyuu", "Kuroko no Basket", "Captain Tsubasa", "Slam Dunk", "Hajime no Ippo",
            // Slice of Life/Romance
            "Your Name", "Kimi no Na wa", "Weathering with You", "A Silent Voice", "Violet Evergarden",
            "Toradora", "Clannad", "Your Lie in April", "Fruits Basket", "Horimiya",
            // Isekai
            "Sword Art Online", "Re:Zero", "Overlord", "Konosuba", "That Time I Got Reincarnated as a Slime",
            // Mecha
            "Neon Genesis Evangelion", "Gundam", "Code Geass", "Darling in the Franxx", "Gurren Lagann",
            // Personajes icónicos
            "Goku", "Luffy", "Naruto Uzumaki", "Ichigo", "Tanjiro",
            "Saitama", "Eren Yeager", "Light Yagami", "Levi Ackerman", "Kakashi"
        )
    )
    
    // Descripciones de las palabras
    private val wordDescriptions = mapOf(
        // Música - Rap Chileno
        "Bubaseta" to "Rapero chileno, pionero del hip hop nacional",
        "Chystemc" to "MC chileno, famoso por su flow y lírica compleja",
        "Portavoz" to "Rapero chileno, conocido por sus letras conscientes",
        "Jonas Sanche" to "Rapero chileno, miembro de Zaturno",
        "Jota Mayúscula" to "MC chileno de freestyle y batalla",
        "Camilo Masilla" to "Rapero chileno, productor musical",
        "Drefquila" to "Rapero chileno urbano y trap",
        "Pablo Chill-E" to "Trap chileno, exponente del género urbano",
        "Polimá Westcoast" to "Artista urbano chileno de trap y reggaeton",
        "Harry Nach" to "Cantante chileno de trap y R&B",
        // Rap Internacional
        "Eminem" to "Rapero estadounidense, uno de los más vendidos de la historia",
        "Tupac" to "Rapero legendario, ícono del hip hop de los 90",
        "Kendrick Lamar" to "Rapero ganador de Pulitzer, líder del rap consciente",
        "Drake" to "Rapero y cantante canadiense, domina las listas",
        "J Cole" to "Rapero y productor, conocido por su lírica introspectiva",
        "Canserbero" to "Rapero venezolano, leyenda del hip hop latinoamericano",
        "Residente" to "Rapero puertorriqueño, ex Calle 13, activista",
        "Nicki Jam" to "Cantante de reggaeton, éxitos mundiales",
        "Bad Bunny" to "Artista urbano puertorriqueño, líder del trap latino",
        "Daddy Yankee" to "Rey del reggaeton, creador de Gasolina",
        // Pop/Rock
        "Michael Jackson" to "Rey del Pop, artista más influyente del siglo XX",
        "Queen" to "Banda británica de rock, liderada por Freddie Mercury",
        "Beatles" to "Banda inglesa más influyente de la historia",
        "Coldplay" to "Banda británica de rock alternativo",
        "Imagine Dragons" to "Banda estadounidense de rock alternativo",
        "Bruno Mars" to "Cantante y productor de pop, funk y R&B",
        "Ed Sheeran" to "Cantautor británico de pop",
        "Taylor Swift" to "Cantante estadounidense de pop y country",
        "Ariana Grande" to "Cantante de pop con poderosa voz",
        "The Weeknd" to "Cantante canadiense de R&B y pop",
        // Reggaeton/Latino
        "Ozuna" to "Cantante puertorriqueño de reggaeton",
        "Maluma" to "Cantante colombiano de reggaeton",
        "Shakira" to "Cantante colombiana, icono internacional",
        "Karol G" to "Cantante colombiana de reggaeton y urbano",
        "J Balvin" to "Cantante colombiano, líder del reggaeton",
        // Canciones
        "Bohemian Rhapsody" to "Épica canción de Queen de 6 minutos",
        "Thriller" to "Canción de Michael Jackson, video icónico",
        "Despacito" to "Reggaeton de Luis Fonsi, récord en YouTube",
        "Shape of You" to "Hit de Ed Sheeran, canción más reproducida",
        "Blinding Lights" to "Éxito de The Weeknd estilo años 80",
        "Hotel California" to "Clásico rock de Eagles",
        "Stairway to Heaven" to "Canción emblemática de Led Zeppelin",
        "Smells Like Teen Spirit" to "Himno del grunge por Nirvana",
        
        // Personajes - Marvel
        "Spider-Man" to "Superhéroe arácnido de Marvel, Peter Parker",
        "Iron Man" to "Tony Stark, genio millonario con armadura",
        "Capitán América" to "Steve Rogers, supersoldado con escudo",
        "Thor" to "Dios del trueno de Asgard con martillo Mjolnir",
        "Hulk" to "Bruce Banner transformado en gigante verde",
        "Viuda Negra" to "Espía rusa, maestra del combate",
        "Doctor Strange" to "Hechicero supremo, maestro de las artes místicas",
        "Black Panther" to "Rey de Wakanda, T'Challa",
        "Wolverine" to "Mutante con garras de adamantium",
        "Deadpool" to "Mercenario regenerativo que rompe la cuarta pared",
        "Thanos" to "Titán loco que busca las gemas del infinito",
        "Loki" to "Dios del engaño, hermano de Thor",
        "Scarlet Witch" to "Wanda Maximoff, hechicera con poderes caóticos",
        "Venom" to "Simbionte alienígena negro",
        "Groot" to "Árbol alienígena que solo dice 'Yo soy Groot'",
        // DC
        "Batman" to "Vigilante de Gotham, Bruce Wayne",
        "Superman" to "Último hijo de Krypton, Clark Kent",
        "Wonder Woman" to "Princesa amazona Diana Prince",
        "Flash" to "Velocista más rápido del mundo",
        "Aquaman" to "Rey de Atlantis, controla el océano",
        "Joker" to "Archienemigo de Batman, payaso del crimen",
        "Harley Quinn" to "Psiquiatra convertida en villana enamorada del Joker",
        "Cyborg" to "Mitad humano, mitad máquina",
        "Green Lantern" to "Guardián con anillo de poder verde",
        "Lex Luthor" to "Genio millonario, enemigo de Superman",
        // Harry Potter
        "Harry Potter" to "El niño que vivió, mago con cicatriz de rayo",
        "Hermione Granger" to "Bruja brillante, mejor amiga de Harry",
        "Ron Weasley" to "Mejor amigo pelirrojo de Harry",
        "Dumbledore" to "Director de Hogwarts, mago más poderoso",
        "Voldemort" to "El señor oscuro, el que no debe ser nombrado",
        "Severus Snape" to "Profesor de pociones, leal a Dumbledore",
        "Hagrid" to "Guardabosques gigante y amable",
        "Draco Malfoy" to "Rival de Harry, mortífago",
        "Sirius Black" to "Padrino de Harry, animago perro",
        "Dobby" to "Elfo doméstico libre y leal",
        // Actores
        "Leonardo DiCaprio" to "Actor de Titanic, El Lobo de Wall Street",
        "Tom Hanks" to "Actor de Forrest Gump, Toy Story",
        "Denzel Washington" to "Actor ganador de 2 Oscars",
        "Morgan Freeman" to "Actor de voz distintiva, narrador icónico",
        "Robert Downey Jr" to "Actor que interpreta a Iron Man",
        "Keanu Reeves" to "Actor de Matrix y John Wick",
        "Will Smith" to "Actor de Men in Black, Príncipe del Rap",
        "Brad Pitt" to "Actor de Fight Club, Troya",
        "Johnny Depp" to "Actor de Piratas del Caribe, Jack Sparrow",
        "Al Pacino" to "Actor de El Padrino, Scarface",
        // Históricos
        "Albert Einstein" to "Físico alemán, teoría de la relatividad",
        "Nikola Tesla" to "Inventor serbio, pionero de la electricidad",
        "Steve Jobs" to "Cofundador de Apple, visionario tecnológico",
        "Pablo Escobar" to "Narcotraficante colombiano",
        "Che Guevara" to "Revolucionario argentino-cubano",
        "Nelson Mandela" to "Líder sudafricano anti-apartheid",
        "Martin Luther King" to "Líder de derechos civiles en EE.UU.",
        "Gandhi" to "Líder independentista indio, no violencia",
        "Cleopatra" to "Última reina del antiguo Egipto",
        "Napoleón Bonaparte" to "Emperador francés, conquistador militar",
        // Otros
        "Augusto Pinochet" to "Dictador militar chileno 1973-1990",
        "Salvador Allende" to "Presidente socialista chileno",
        "Arturo Vidal" to "Futbolista chileno, mediocampista",
        "Alexis Sánchez" to "Futbolista chileno, delantero estrella",
        "Gabriela Mistral" to "Poetisa chilena, Premio Nobel de Literatura",
        
        // Películas - Acción
        "John Wick" to "Asesino retirado que busca venganza por su perro",
        "Matrix" to "Mundo virtual controlado por máquinas",
        "Die Hard" to "Policía contra terroristas en un rascacielos",
        "Mad Max" to "Guerrero en un apocalipsis desértico",
        "Terminator" to "Robot asesino enviado del futuro",
        "Rambo" to "Veterano de guerra en misiones imposibles",
        "Jason Bourne" to "Agente con amnesia descubriendo su pasado",
        "Mission Impossible" to "Agente Ethan Hunt en misiones extremas",
        "Fast and Furious" to "Carreras ilegales y atracos con autos",
        "The Equalizer" to "Justiciero retirado que ayuda a inocentes",
        // Aventura
        "Indiana Jones" to "Arqueólogo aventurero con látigo",
        "Piratas del Caribe" to "Jack Sparrow y piratas en alta mar",
        "Jurassic Park" to "Parque de dinosaurios clonados",
        "Avatar" to "Humanos en planeta alienígena Pandora",
        "El Señor de los Anillos" to "Épica aventura para destruir el anillo único",
        "Star Wars" to "Guerra de las galaxias con la Fuerza",
        "Gladiador" to "Guerrero romano busca venganza en la arena",
        "Braveheart" to "William Wallace lucha por Escocia",
        "Troya" to "Guerra épica por Helena de Troya",
        "300" to "Espartanos contra ejército persa",
        // Superhéroes
        "Avengers" to "Equipo de superhéroes de Marvel",
        "The Dark Knight" to "Batman vs Joker, la mejor de superhéroes",
        "Man of Steel" to "Origen de Superman",
        "Guardianes de la Galaxia" to "Equipo espacial de héroes inadaptados",
        "Logan" to "Wolverine envejecido en su última misión",
        "Black Panther" to "Rey de Wakanda protege su nación",
        "Spider-Man No Way Home" to "Spider-Man y el multiverso",
        "Deadpool" to "Antihéroe irreverente y violento",
        "Joker" to "Origen oscuro del villano de Gotham",
        "Wonder Woman" to "Amazona en la Primera Guerra Mundial",
        // Clásicos
        "El Padrino" to "Saga de la mafia italiana Corleone",
        "Titanic" to "Romance trágico en el barco hundido",
        "Forrest Gump" to "Hombre simple que vive grandes momentos",
        "Pulp Fiction" to "Historias entrelazadas del crimen",
        "Shawshank Redemption" to "Escape épico de prisión",
        "El Club de la Pelea" to "Grupos secretos de lucha clandestina",
        "Inception" to "Robo dentro de sueños en múltiples niveles",
        "Interstellar" to "Viaje espacial para salvar la humanidad",
        "El Origen" to "Thriller de Christopher Nolan sobre sueños",
        "Parasite" to "Familia pobre infiltra casa de ricos",
        // Terror
        "El Conjuro" to "Investigadores paranormales de casos reales",
        "El Exorcista" to "Niña poseída por demonio",
        "Halloween" to "Michael Myers asesino enmascarado",
        "El Silencio de los Inocentes" to "FBI busca a asesino serial caníbal",
        "Psicosis" to "Motel con secreto asesino",
        "It" to "Payaso demoníaco aterroriza pueblo",
        "El Resplandor" to "Hotel embrujado vuelve loco a escritor",
        "Scream" to "Asesino con máscara Ghostface",
        "Saw" to "Juegos mortales de Jigsaw",
        "Hereditary" to "Terror familiar con secta demoníaca",
        // Comedia
        "Mi Pobre Angelito" to "Niño solo defiende casa de ladrones",
        "Scary Movie" to "Parodia de películas de terror",
        "Superbad" to "Adolescentes buscan alcohol para fiesta",
        "The Hangover" to "Resaca en Las Vegas con amnesia",
        "Ted" to "Oso de peluche viviente y grosero",
        
        // Videojuegos - Acción/Aventura
        "God of War" to "Kratos contra dioses griegos y nórdicos",
        "The Last of Us" to "Sobrevivientes en apocalipsis zombie",
        "Uncharted" to "Cazatesoros Nathan Drake",
        "Red Dead Redemption" to "Forajido en el viejo oeste",
        "GTA" to "Crimen y caos en ciudad abierta",
        "Assassins Creed" to "Asesinos históricos con parkour",
        "Batman Arkham" to "Batman en Gotham combatiendo villanos",
        "Tomb Raider" to "Lara Croft exploradora de tumbas",
        "Horizon Zero Dawn" to "Cazadora en mundo post-apocalíptico con robots",
        "Ghost of Tsushima" to "Samurái defiende Japón de mongoles",
        // Shooter
        "Call of Duty" to "Guerra moderna en primera persona",
        "Battlefield" to "Batallas masivas militares",
        "Halo" to "Soldado espacial contra aliens",
        "Destiny" to "Shooter espacial con poderes",
        "Counter Strike" to "Terroristas vs antiterroristas",
        "Valorant" to "Shooter táctico con habilidades",
        "Overwatch" to "Héroes con habilidades únicas",
        "Apex Legends" to "Battle royale con leyendas",
        "Fortnite" to "Battle royale con construcción",
        "PUBG" to "Battle royale realista",
        // RPG
        "The Witcher" to "Geralt de Rivia cazador de monstruos",
        "Skyrim" to "RPG de dragones y magia nórdica",
        "Dark Souls" to "RPG ultra difícil de fantasía oscura",
        "Elden Ring" to "RPG de From Software y George R.R. Martin",
        "Final Fantasy" to "RPG japonés épico con historia",
        "Dragon Age" to "RPG de fantasía con dragones",
        "Mass Effect" to "RPG espacial con aliens",
        "Cyberpunk 2077" to "RPG futurista en Night City",
        "Persona" to "RPG japonés con vida escolar",
        "Bloodborne" to "RPG gótico de horror victoriano",
        // Deportes
        "FIFA" to "Simulador de fútbol de EA Sports",
        "NBA 2K" to "Simulador de baloncesto",
        "Madden NFL" to "Simulador de fútbol americano",
        "WWE 2K" to "Simulador de lucha libre",
        "Rocket League" to "Fútbol con autos voladores",
        // Horror
        "Resident Evil" to "Survival horror con zombies",
        "Silent Hill" to "Terror psicológico en pueblo maldito",
        "Dead Space" to "Terror espacial con necromorphs",
        "The Evil Within" to "Horror psicológico de Shinji Mikami",
        "Outlast" to "Terror en primera persona sin armas",
        // Plataformas
        "Super Mario" to "Fontanero salta y rescata princesa",
        "Zelda" to "Link salva a Zelda en Hyrule",
        "Crash Bandicoot" to "Marsupial que gira y salta",
        "Sonic" to "Erizo azul súper veloz",
        "Spyro" to "Dragón morado que escupe fuego",
        "Ratchet and Clank" to "Lombax y robot con armas locas",
        "Sackboy" to "Muñeco de tela en mundos creativos",
        "Astro Bot" to "Robot en aventuras VR de PlayStation",
        "Donkey Kong" to "Gorila que lanza barriles",
        "Kirby" to "Bola rosa que absorbe poderes",
        // Online
        "League of Legends" to "MOBA competitivo de Riot Games",
        "Dota 2" to "MOBA de Valve, el más competitivo",
        "Minecraft" to "Construye y sobrevive con bloques",
        "Roblox" to "Plataforma de juegos creados por usuarios",
        "Among Us" to "Encuentra al impostor en la nave",
        "Fall Guys" to "Battle royale de obstáculos divertidos",
        "World of Warcraft" to "MMORPG de fantasía de Blizzard",
        "Genshin Impact" to "RPG anime gacha de mundo abierto",
        "Warzone" to "Battle royale de Call of Duty",
        
        // Anime - Shonen
        "Naruto" to "Ninja con zorro de 9 colas busca ser Hokage",
        "One Piece" to "Piratas buscan el tesoro One Piece",
        "Bleach" to "Shinigami que protege el mundo",
        "Dragon Ball" to "Goku y las esferas del dragón",
        "My Hero Academia" to "Escuela de superhéroes en Japón",
        "Demon Slayer" to "Cazador de demonios para salvar hermana",
        "Kimetsu no Yaiba" to "Tanjiro caza demonios con espada",
        "Jujutsu Kaisen" to "Hechiceros combaten maldiciones",
        "Chainsaw Man" to "Denji con motosierra por cabeza",
        "Black Clover" to "Mago sin magia busca ser Rey Mago",
        "One Punch Man" to "Héroe calvo que derrota todo de un golpe",
        "Mob Psycho" to "Estudiante con poderes psíquicos",
        "Hunter x Hunter" to "Gon busca a su padre cazador",
        "Fullmetal Alchemist" to "Hermanos alquimistas buscan piedra filosofal",
        "Attack on Titan" to "Humanos vs titanes devoradores",
        // Seinen
        "Berserk" to "Espadachín con espada gigante en mundo oscuro",
        "Tokyo Ghoul" to "Humano convertido en ghoul come-humanos",
        "Parasyte" to "Parásito alienígena en mano",
        "Death Note" to "Cuaderno que mata con solo escribir nombres",
        "Code Geass" to "Príncipe con poder de control mental",
        "Steins Gate" to "Viajes en el tiempo con microondas",
        "Cowboy Bebop" to "Cazarrecompensas espaciales con jazz",
        "Samurai Champloo" to "Samuráis con hip hop",
        "Vinland Saga" to "Vikingos en busca de tierra prometida",
        "Monster" to "Doctor persigue asesino serial que salvó",
        // Deportes
        "Haikyuu" to "Equipo de voleibol busca ser campeón",
        "Kuroko no Basket" to "Basquetbolista fantasma con pases",
        "Captain Tsubasa" to "Futbolista Oliver Atom",
        "Slam Dunk" to "Delincuente se une a equipo de basket",
        "Hajime no Ippo" to "Boxeador tímido se vuelve campeón",
        // Romance
        "Your Name" to "Pareja intercambia cuerpos en sueños",
        "Kimi no Na wa" to "Tu nombre en japonés, romance temporal",
        "Weathering with You" to "Chica que controla el clima",
        "A Silent Voice" to "Bully se redime con chica sorda",
        "Violet Evergarden" to "Soldado aprende sobre amor escribiendo cartas",
        "Toradora" to "Estudiantes se ayudan con sus amores",
        "Clannad" to "Romance escolar dramático y emotivo",
        "Your Lie in April" to "Pianista y violinista romance musical",
        "Fruits Basket" to "Chica vive con familia maldita zodiacal",
        "Horimiya" to "Romance escolar de opuestos",
        // Isekai
        "Sword Art Online" to "Atrapados en videojuego virtual MMORPG",
        "Re:Zero" to "Chico muere y reinicia en otro mundo",
        "Overlord" to "Jugador atrapado como esqueleto rey",
        "Konosuba" to "Aventureros incompetentes en otro mundo",
        "That Time I Got Reincarnated as a Slime" to "Oficinista renace como slime poderoso",
        // Mecha
        "Neon Genesis Evangelion" to "Adolescentes pilotan robots contra ángeles",
        "Gundam" to "Robots gigantes en guerras espaciales",
        "Darling in the Franxx" to "Parejas pilotean mechas contra monstruos",
        "Gurren Lagann" to "Humanos con robots gigantes perforan cielos",
        // Personajes
        "Goku" to "Saiyajin protagonista de Dragon Ball",
        "Luffy" to "Pirata de goma que busca One Piece",
        "Naruto Uzumaki" to "Ninja con kyubi que busca ser Hokage",
        "Ichigo" to "Shinigami sustituto de Bleach",
        "Tanjiro" to "Cazador de demonios con hermana demonio",
        "Saitama" to "Héroe calvo invencible de One Punch Man",
        "Eren Yeager" to "Humano que se transforma en titán",
        "Light Yagami" to "Estudiante con Death Note",
        "Levi Ackerman" to "Soldado más fuerte de la humanidad",
        "Kakashi" to "Ninja que copia jutsus, sensei de Naruto"
    )
    
    // Pistas específicas para impostores según la palabra
    private val wordClues = mapOf(
        // Música - Rap Chileno
        "Bubaseta" to "Hip hop chileno, grupo de rap",
        "Chystemc" to "Rapero con flow rápido",
        "Portavoz" to "MC chileno de letras profundas",
        "Jonas Sanche" to "Rapero de Zaturno",
        "Jota Mayúscula" to "Freestyle y batallas",
        "Camilo Masilla" to "Productor de beats",
        "Drefquila" to "Trap chileno urbano",
        "Pablo Chill-E" to "Trap, música urbana",
        "Polimá Westcoast" to "Artista de trap latino",
        "Harry Nach" to "R&B y trap chileno",
        // Rap Internacional
        "Eminem" to "Rapero blanco estadounidense",
        "Tupac" to "Rapero legendario de los 90",
        "Kendrick Lamar" to "Rap consciente, Pulitzer",
        "Drake" to "Rapero canadiense de éxito",
        "J Cole" to "Rapero introspectivo",
        "Canserbero" to "Rapero venezolano legendario",
        "Residente" to "Ex Calle 13, activista",
        "Nicki Jam" to "Reggaeton romántico",
        "Bad Bunny" to "Trap latino puertorriqueño",
        "Daddy Yankee" to "Gasolina, rey del reggaeton",
        // Pop/Rock
        "Michael Jackson" to "Moonwalk, Thriller",
        "Queen" to "Banda con Freddie Mercury",
        "Beatles" to "Banda inglesa de los 60",
        "Coldplay" to "Rock alternativo británico",
        "Imagine Dragons" to "Radioactive, rock moderno",
        "Bruno Mars" to "Funk y pop moderno",
        "Ed Sheeran" to "Cantautor pelirrojo",
        "Taylor Swift" to "Cantante de pop y country",
        "Ariana Grande" to "Voz poderosa, pop",
        "The Weeknd" to "Blinding Lights, R&B",
        // Reggaeton/Latino
        "Ozuna" to "Reggaeton puertorriqueño",
        "Maluma" to "Reggaeton colombiano",
        "Shakira" to "Waka Waka, cantante latina",
        "Karol G" to "Bichota, reggaeton femenino",
        "J Balvin" to "Mi Gente, reggaeton",
        // Canciones
        "Bohemian Rhapsody" to "Canción de 6 minutos de Queen",
        "Thriller" to "Video con zombies de MJ",
        "Despacito" to "Reggaeton viral en YouTube",
        "Shape of You" to "Hit de Ed Sheeran",
        "Blinding Lights" to "Synth pop años 80",
        "Hotel California" to "Rock clásico de Eagles",
        "Stairway to Heaven" to "Canción épica de Led Zeppelin",
        "Smells Like Teen Spirit" to "Grunge, Nirvana",
        
        // Personajes - Marvel
        "Spider-Man" to "Arácnido trepa muros",
        "Iron Man" to "Armadura tecnológica roja",
        "Capitán América" to "Escudo, supersoldado",
        "Thor" to "Martillo, dios nórdico",
        "Hulk" to "Gigante verde enojado",
        "Viuda Negra" to "Espía rusa pelirroja",
        "Doctor Strange" to "Hechicero con capa",
        "Black Panther" to "Rey africano, Wakanda",
        "Wolverine" to "Garras de metal",
        "Deadpool" to "Mercenario con traje rojo",
        "Thanos" to "Villano morado con guante",
        "Loki" to "Dios del engaño, cuernos",
        "Scarlet Witch" to "Magia roja, Wanda",
        "Venom" to "Simbionte negro con lengua",
        "Groot" to "Árbol que habla poco",
        // DC
        "Batman" to "Murciélago, millonario",
        "Superman" to "Capa roja, vuela, kryptonita",
        "Wonder Woman" to "Lazo de la verdad, amazona",
        "Flash" to "Velocista con rayo",
        "Aquaman" to "Rey del océano, tridente",
        "Joker" to "Payaso villano loco",
        "Harley Quinn" to "Novia del Joker, mazo",
        "Cyborg" to "Mitad robot",
        "Green Lantern" to "Anillo de poder verde",
        "Lex Luthor" to "Calvo enemigo de Superman",
        // Harry Potter
        "Harry Potter" to "Cicatriz de rayo, mago",
        "Hermione Granger" to "Sabelotodo, mejor amiga",
        "Ron Weasley" to "Pelirrojo, mejor amigo",
        "Dumbledore" to "Director de Hogwarts",
        "Voldemort" to "Villano sin nariz",
        "Severus Snape" to "Profesor de pociones",
        "Hagrid" to "Guardabosques gigante",
        "Draco Malfoy" to "Rubio rival de Slytherin",
        "Sirius Black" to "Padrino, se convierte en perro",
        "Dobby" to "Elfo con calcetín",
        // Actores
        "Leonardo DiCaprio" to "Titanic, Inception",
        "Tom Hanks" to "Forrest Gump, voz de Woody",
        "Denzel Washington" to "Actor afroamericano",
        "Morgan Freeman" to "Voz grave, narrador",
        "Robert Downey Jr" to "Actor de Iron Man",
        "Keanu Reeves" to "Matrix, Neo, John Wick",
        "Will Smith" to "Príncipe del rap",
        "Brad Pitt" to "Fight Club, guapo",
        "Johnny Depp" to "Jack Sparrow, pirata",
        "Al Pacino" to "Scarface, Di Hola",
        // Históricos
        "Albert Einstein" to "E=mc², físico genio",
        "Nikola Tesla" to "Electricidad, inventos",
        "Steve Jobs" to "Apple, iPhone, CEO",
        "Pablo Escobar" to "Narcotraficante, cocaína",
        "Che Guevara" to "Revolucionario, boina",
        "Nelson Mandela" to "Sudáfrica, presidente preso",
        "Martin Luther King" to "I have a dream",
        "Gandhi" to "India, no violencia",
        "Cleopatra" to "Reina egipcia bella",
        "Napoleón Bonaparte" to "Emperador francés bajo",
        // Otros
        "Augusto Pinochet" to "Dictador chileno, golpe",
        "Salvador Allende" to "Presidente socialista chileno",
        "Arturo Vidal" to "Futbolista chileno, Rey",
        "Alexis Sánchez" to "Niño Maravilla, delantero",
        "Gabriela Mistral" to "Poetisa chilena, Nobel",
        
        // Películas - Acción
        "John Wick" to "Asesino con perro",
        "Matrix" to "Píldora roja, Neo",
        "Die Hard" to "Yippee Ki Yay",
        "Mad Max" to "Desierto post-apocalíptico",
        "Terminator" to "I'll be back, robot",
        "Rambo" to "Veterano con cinta",
        "Jason Bourne" to "Amnesia, agente",
        "Mission Impossible" to "Tom Cruise, acrobacias",
        "Fast and Furious" to "Carreras, familia",
        "The Equalizer" to "Denzel Washington, justiciero",
        // Aventura
        "Indiana Jones" to "Arqueólogo con látigo",
        "Piratas del Caribe" to "Jack Sparrow, barcos",
        "Jurassic Park" to "Parque de dinosaurios",
        "Avatar" to "Alienígenas azules, Pandora",
        "El Señor de los Anillos" to "Anillo, hobbits, Frodo",
        "Star Wars" to "La Fuerza, Darth Vader",
        "Gladiador" to "Are you entertained?",
        "Braveheart" to "Escocia, Freedom",
        "Troya" to "Caballo de madera, Helena",
        "300" to "This is Sparta!",
        // Superhéroes
        "Avengers" to "Equipo de superhéroes Marvel",
        "The Dark Knight" to "Batman vs Joker",
        "Man of Steel" to "Origen de Superman",
        "Guardianes de la Galaxia" to "Groot, Rocket, espacial",
        "Logan" to "Wolverine viejo",
        "Black Panther" to "Wakanda Forever",
        "Spider-Man No Way Home" to "Multiverso, tres arañas",
        "Deadpool" to "Traje rojo, merc con boca",
        "Joker" to "Escaleras, baile",
        "Wonder Woman" to "Gal Gadot, amazona",
        // Clásicos
        "El Padrino" to "Mafia, cabeza de caballo",
        "Titanic" to "Barco hundido, Jack y Rose",
        "Forrest Gump" to "Corre Forrest, cajas",
        "Pulp Fiction" to "Tarantino, Vincent Vega",
        "Shawshank Redemption" to "Escape de prisión, Andy",
        "El Club de la Pelea" to "No hablar del club",
        "Inception" to "Sueños dentro de sueños",
        "Interstellar" to "Espacio, agujero negro",
        "El Origen" to "Sueños, Christopher Nolan",
        "Parasite" to "Familia pobre infiltra",
        // Terror
        "El Conjuro" to "Warren, casa embrujada",
        "El Exorcista" to "Niña poseída, cabeza gira",
        "Halloween" to "Michael Myers, máscara",
        "El Silencio de los Inocentes" to "Hannibal Lecter, FBI",
        "Psicosis" to "Motel, ducha, cuchillo",
        "It" to "Payaso Pennywise, globos",
        "El Resplandor" to "Hotel, All work no play",
        "Scream" to "Ghostface, teléfono",
        "Saw" to "Juegos mortales, muñeco",
        "Hereditary" to "Familia maldita, horror",
        // Comedia
        "Mi Pobre Angelito" to "Niño solo en casa",
        "Scary Movie" to "Parodia de terror",
        "Superbad" to "Adolescentes, fiesta",
        "The Hangover" to "Resaca en Las Vegas",
        "Ted" to "Oso de peluche vivo",
        
        // Videojuegos - Acción/Aventura
        "God of War" to "Kratos, cadenas, dioses",
        "The Last of Us" to "Joel y Ellie, zombies",
        "Uncharted" to "Nathan Drake, tesoros",
        "Red Dead Redemption" to "Vaqueros, oeste",
        "GTA" to "Robo de autos, ciudad",
        "Assassins Creed" to "Asesinos, parkour, capucha",
        "Batman Arkham" to "Batman, Gotham",
        "Tomb Raider" to "Lara Croft, tumbas",
        "Horizon Zero Dawn" to "Aloy, robots dinosaurios",
        "Ghost of Tsushima" to "Samurái, Japón",
        // Shooter
        "Call of Duty" to "COD, guerra moderna",
        "Battlefield" to "Tanques, guerra grande",
        "Halo" to "Master Chief, Spartans",
        "Destiny" to "Guardianes espaciales",
        "Counter Strike" to "CS, terroristas, bombas",
        "Valorant" to "Agentes con habilidades",
        "Overwatch" to "Héroes, Tracer",
        "Apex Legends" to "Battle royale, leyendas",
        "Fortnite" to "Battle royale, bailes",
        "PUBG" to "Battle royale, pan",
        // RPG
        "The Witcher" to "Geralt, brujo, monstruos",
        "Skyrim" to "Dragones, Fus Ro Dah",
        "Dark Souls" to "Muy difícil, You Died",
        "Elden Ring" to "Souls, mundo abierto",
        "Final Fantasy" to "RPG japonés, FF",
        "Dragon Age" to "RPG fantasía, dragones",
        "Mass Effect" to "Shepard, espacial",
        "Cyberpunk 2077" to "Futuro, Night City",
        "Persona" to "RPG japonés, estudiantes",
        "Bloodborne" to "Gótico, victoriano",
        // Deportes
        "FIFA" to "Fútbol de EA Sports",
        "NBA 2K" to "Baloncesto, canastas",
        "Madden NFL" to "Fútbol americano",
        "WWE 2K" to "Lucha libre",
        "Rocket League" to "Fútbol con autos",
        // Horror
        "Resident Evil" to "Zombies, Umbrella",
        "Silent Hill" to "Niebla, terror psicológico",
        "Dead Space" to "Espacio, necromorphs",
        "The Evil Within" to "Horror, Shinji Mikami",
        "Outlast" to "Cámara, sin armas",
        // Plataformas
        "Super Mario" to "Fontanero con bigote",
        "Zelda" to "Link, espada, Hyrule",
        "Crash Bandicoot" to "Marsupial naranja",
        "Sonic" to "Erizo azul veloz",
        "Spyro" to "Dragón morado",
        "Ratchet and Clank" to "Lombax, robot",
        "Sackboy" to "Muñeco de tela",
        "Astro Bot" to "Robot PlayStation",
        "Donkey Kong" to "Gorila, barriles",
        "Kirby" to "Rosa que absorbe",
        // Online
        "League of Legends" to "LoL, MOBA, campeones",
        "Dota 2" to "MOBA de Valve",
        "Minecraft" to "Bloques, creeper, Steve",
        "Roblox" to "Juegos de usuarios, Oof",
        "Among Us" to "Impostor, tripulantes",
        "Fall Guys" to "Caídas graciosas",
        "World of Warcraft" to "WoW, MMORPG, orcos",
        "Genshin Impact" to "Gacha, anime, Paimon",
        "Warzone" to "Battle royale de COD",
        
        // Anime - Shonen
        "Naruto" to "Ninja con zorro, Konoha",
        "One Piece" to "Piratas, Luffy, sombrero",
        "Bleach" to "Shinigami, espada grande",
        "Dragon Ball" to "Goku, Super Saiyan",
        "My Hero Academia" to "Plus Ultra, Deku",
        "Demon Slayer" to "Espadas, respiración",
        "Kimetsu no Yaiba" to "Tanjiro, hermana demonio",
        "Jujutsu Kaisen" to "Hechicería, maldiciones",
        "Chainsaw Man" to "Motosierra por cabeza",
        "Black Clover" to "Grimorio, anti-magia",
        "One Punch Man" to "Calvo invencible, Saitama",
        "Mob Psycho" to "Poderes psíquicos, estudiante",
        "Hunter x Hunter" to "Cazadores, Gon",
        "Fullmetal Alchemist" to "Hermanos alquimistas, brazo",
        "Attack on Titan" to "Titanes gigantes, muros",
        // Seinen
        "Berserk" to "Espada enorme, Guts",
        "Tokyo Ghoul" to "Kaneki, ojo rojo",
        "Parasyte" to "Mano alienígena",
        "Death Note" to "Cuaderno de muerte, L",
        "Code Geass" to "Geass, Lelouch, mecha",
        "Steins Gate" to "Viajes en tiempo, El Psy",
        "Cowboy Bebop" to "Cazarrecompensas, jazz",
        "Samurai Champloo" to "Samuráis con hip hop",
        "Vinland Saga" to "Vikingos, Thorfinn",
        "Monster" to "Doctor, asesino serial",
        // Deportes
        "Haikyuu" to "Voleibol, Hinata salta",
        "Kuroko no Basket" to "Basket, pases invisibles",
        "Captain Tsubasa" to "Fútbol, Oliver Atom",
        "Slam Dunk" to "Basket, Sakuragi",
        "Hajime no Ippo" to "Boxeo, Ippo pelea",
        // Romance
        "Your Name" to "Intercambio de cuerpos",
        "Kimi no Na wa" to "Tu nombre, cometa",
        "Weathering with You" to "Chica del clima",
        "A Silent Voice" to "Chica sorda, bullying",
        "Violet Evergarden" to "Cartas, brazos mecánicos",
        "Toradora" to "Romance escolar, Taiga",
        "Clannad" to "Romance dramático",
        "Your Lie in April" to "Piano, violín, música",
        "Fruits Basket" to "Zodíaco chino, maldición",
        "Horimiya" to "Pareja escolar",
        // Isekai
        "Sword Art Online" to "SAO, MMORPG atrapados",
        "Re:Zero" to "Muere y reinicia",
        "Overlord" to "Esqueleto rey, NPCs",
        "Konosuba" to "Comedia, otro mundo",
        "That Time I Got Reincarnated as a Slime" to "Slime azul OP",
        // Mecha
        "Neon Genesis Evangelion" to "EVA, robots, ángeles",
        "Gundam" to "Robots gigantes guerra",
        "Darling in the Franxx" to "Mechas, Zero Two",
        "Gurren Lagann" to "Taladro, robots enormes",
        // Personajes
        "Goku" to "Kakaroto, Super Saiyan",
        "Luffy" to "Goma goma, Rey Pirata",
        "Naruto Uzumaki" to "Dattebayo, kyubi",
        "Ichigo" to "Pelo naranja, zanpakutō",
        "Tanjiro" to "Orejas de hanafuda",
        "Saitama" to "Un golpe, héroe calvo",
        "Eren Yeager" to "Tatakae, titán de ataque",
        "Light Yagami" to "Kira, cuaderno",
        "Levi Ackerman" to "Capitán, limpieza",
        "Kakashi" to "Sharingan copiador, sensei"
    )
    
    // Pistas para impostores según el tema
    private val themeClues = mapOf(
        MusicTheme.MUSIC to "Relacionado con música, artistas o canciones famosas",
        MusicTheme.CHARACTERS to "Relacionado con personajes de películas, cómics, historia o famosos",
        MusicTheme.MOVIES to "Relacionado con películas famosas de cualquier género",
        MusicTheme.GAMES to "Relacionado con videojuegos populares",
        MusicTheme.ANIME to "Relacionado con anime, manga o personajes japoneses"
    )
    
    fun updateConfig(config: GameConfig) {
        gameConfig = config
    }
    
    fun startGame() {
        val theme = gameConfig.musicTheme ?: MusicTheme.MUSIC
        val words = themeWords[theme] ?: themeWords[MusicTheme.MUSIC]!!
        
        // Seleccionar una palabra para los inocentes
        val selectedWord = words.random()
        val description = wordDescriptions[selectedWord] ?: ""
        
        // La pista del impostor siempre está relacionada con la palabra correcta
        val clueForImpostor = wordClues[selectedWord] ?: themeClues[theme] ?: "Adivina la palabra"
        
        // Crear lista de jugadores
        val playerList = mutableListOf<Player>()
        val impostorIndices = (0 until gameConfig.totalPlayers)
            .shuffled()
            .take(gameConfig.totalImpostors)
            .toSet()
        
        for (i in 0 until gameConfig.totalPlayers) {
            val isImpostor = i in impostorIndices
            val word = if (isImpostor) {
                if (gameConfig.giveClueToImpostor) {
                    clueForImpostor
                } else {
                    "IMPOSTOR"
                }
            } else {
                selectedWord
            }
            val desc = if (isImpostor) "" else description
            playerList.add(Player(i + 1, isImpostor, word, desc))
        }
        
        players = playerList
        currentPlayerIndex = 0
        isGameStarted = true
        showVotingScreen = false
        votes = emptyMap()
    }
    
    fun nextPlayer() {
        if (currentPlayerIndex < players.size - 1) {
            currentPlayerIndex++
        } else {
            // Todos vieron su palabra, iniciar votación
            showVotingScreen = true
        }
    }
    
    fun vote(playerId: Int) {
        votes = votes.toMutableMap().apply {
            this[playerId] = (this[playerId] ?: 0) + 1
        }
    }
    
    fun getVoteResults(): List<VoteResult> {
        return votes.map { (playerId, voteCount) ->
            VoteResult(playerId, voteCount)
        }.sortedByDescending { it.votes }
    }
    
    fun resetGame() {
        gameConfig = GameConfig()
        players = emptyList()
        currentPlayerIndex = 0
        isGameStarted = false
        showVotingScreen = false
        votes = emptyMap()
    }
}
