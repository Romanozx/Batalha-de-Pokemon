fun main() {
    val limitePokemons = 3

    val jogador1 = criarJogador("Jogador A", limitePokemons)
    val jogador2 = criarJogador("Jogador B", limitePokemons)

    println("\n${BOLD}Time do ${jogador1["nickname"]}:$RESET")
    exibirTime(jogador1["time"] as List<Map<String, Any>>)

    println("\n${BOLD}Time do ${jogador2["nickname"]}:$RESET")
    exibirTime(jogador2["time"] as List<Map<String, Any>>)

    println("\n${BOLD}Iniciando batalha...$RESET\n")
    val vencedor = batalhar(jogador1, jogador2)
    println("\n${BOLD}${WHITE}Vencedor da batalha: $vencedor$RESET")
}

fun criarJogador(nickname: String, limite: Int): Map<String, Any> {
    val time = mutableListOf<Map<String, Any>>()

    repeat(limite) { index ->

        val nome = "Pokemon${index + 1}"
        val tipo = listOf("Fogo", "Água", "Planta", "Elétrico").random()
        val nivel = (1..50).random()
        val ataque = (10..20).random()
        val defesa = (5..15).random()
        val habilidade = listOf("Crítico", "Regenerar", "Carga", "Fúria").random()

        val pokemon = mapOf(
            "nome" to nome,
            "tipo" to tipo,
            "nível" to nivel,
            "ataque" to ataque,
            "defesa" to defesa,
            "habilidade" to habilidade
        )

        time.add(pokemon)
    }

    return mapOf(
        "nickname" to nickname,
        "time" to time
    )
}

fun exibirTime(time: List<Map<String, Any>>) {
    for ((index, poke) in time.withIndex()) {
        println(" ${index + 1}. ${GREEN}${poke["nome"]}$RESET | " +
                "Tipo: ${BLUE}${poke["tipo"]}$RESET | " +
                "Nível: ${YELLOW}${poke["nível"]}$RESET | " +
                "ATK: ${RED}${poke["ataque"]}$RESET | " +
                "DEF: ${CYAN}${poke["defesa"]}$RESET | " +
                "Habilidade: ${PURPLE}${poke["habilidade"]}$RESET")
    }
}

fun batalhar(j1: Map<String, Any>, j2: Map<String, Any>): String {
    val time1 = j1["time"] as List<Map<String, Any>>
    val time2 = j2["time"] as List<Map<String, Any>>

    var vitoriasJ1 = 0
    var vitoriasJ2 = 0

    for (i in time1.indices) {
        val p1 = time1[i]
        val p2 = time2[i]

        println("${GREEN}${p1["nome"]}$RESET (de ${j1["nickname"]}) vs ${GREEN}${p2["nome"]}$RESET (de ${j2["nickname"]})")

        val poder1 = calcularPoder(p1)
        val poder2 = calcularPoder(p2)

        println("   Poder total: ${p1["nome"]} = ${YELLOW}$poder1$RESET | ${p2["nome"]} = ${YELLOW}$poder2$RESET")

        when {
            poder1 > poder2 -> {
                println("${BOLD}${p1["nome"]} venceu!$RESET\n")
                vitoriasJ1++
            }
            poder2 > poder1 -> {
                println("${BOLD}${p2["nome"]} venceu!$RESET\n")
                vitoriasJ2++
            }
            else -> println("${BOLD}Empate!$RESET\n")
        }
    }

    return when {
        vitoriasJ1 > vitoriasJ2 -> j1["nickname"].toString()
        vitoriasJ2 > vitoriasJ1 -> j2["nickname"].toString()
        else -> "Empate"
    }
}

fun calcularPoder(pokemon: Map<String, Any>): Int {
    val ataque = pokemon["ataque"] as Int
    val defesa = pokemon["defesa"] as Int
    val nivel = pokemon["nível"] as Int
    val habilidade = pokemon["habilidade"] as String

    val bonus = when (habilidade) {
        "Crítico" -> 5
        "Fúria" -> 3
        "Regenerar" -> 2
        "Carga" -> 4
        else -> 0
    }

    return (ataque * 2 + defesa + nivel / 2 + bonus)
}

const val RESET = "\u001B[0m"
const val BOLD = "\u001B[1m"
const val GREEN = "\u001B[32m"
const val BLUE = "\u001B[34m"
const val YELLOW = "\u001B[33m"
const val RED = "\u001B[31m"
const val CYAN = "\u001B[36m"
const val PURPLE = "\u001B[35m"
const val WHITE = "\u001B[37m"