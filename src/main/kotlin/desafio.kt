import java.time.LocalDate;

object identificador {
    var formacao: Int = 0
    var usuario: Int = 0
    var conteudo: Int = 0
    var atividade: Int = 0
}

enum class Nivel { BASICO, INTERMEDIARIO, DIFICIL }
enum class TipoConteudo { CHALLENGE, PROJECT, CURSO, LIVE}
enum class TipoAtividade { AULA, MATERIAL, TESTE, NONE}
enum class Acesso { FREE, TOP, GLOBAL }

data class Usuario (val nome: String, val nascimento: LocalDate, var apelido: String)

data class Atividade(val nome: String, val nivel: Nivel, val tipoAtividade: TipoAtividade = TipoAtividade.NONE, var acesso: Acesso = Acesso.FREE, var pai: Int = 0, var duracao: Int = 0) {
    val id = ++identificador.atividade
    var instrutor: String = ""
    var link: String = ""
    var stack: String = ""

    fun showDetails() {
        println("Atividade(id=$id, pai=$pai, tipoAtividade=$tipoAtividade, nome=$nome)")
    }
}

data class ConteudoEducacional(val nome: String, val duracao: Int = 0) {
    val id = ++identificador.conteudo
    val atividades = mutableListOf<Atividade>()
    var itens = atividades.size

    fun adicionarAtividade(atividade: Atividade) {
        atividades.add(atividade)
        itens = atividades.size
    }

    fun showDetails() {
        println("id=$id, nome=$nome")
    }
}

data class Formacao(val nome: String, var inicio: String, var conteudos: List<ConteudoEducacional>? = null) {
    val id = ++identificador.formacao
    val inscritos = mutableListOf<Usuario>()

    fun adicionarConteudo(listaConteudos: List<ConteudoEducacional>) {
        conteudos = listaConteudos
    }

    fun matricular(usuario: Usuario) {
        inscritos.add(usuario)
    }

    fun showDetails() {
        println("Formacao(id=$id, inicio=$inicio, nome=$nome)")
    }

    fun showTree() {
        println("Formacao: $nome")
        for (conteudo in conteudos!!) {
            println("\tConteudo Educacional: ${conteudo.nome}")
            for (atividade in conteudo.atividades) {
                var prefix: String
                var label = if (atividade.tipoAtividade == TipoAtividade.NONE) "Atividades: " else "${atividade.tipoAtividade}: "
                if (atividade.pai == 0) {
                    prefix = "\t\t"
                } else {
                    prefix = "\t\t\t"
                }
                println("$prefix$label: ${atividade.nome}")
            }
        }
        print("\n")
    }
}


fun main() {
    var atividade1 = Atividade("Apresentacao e objetivos", Nivel.BASICO, TipoAtividade.AULA)
    atividade1.instrutor = "Gomes Adams"
    atividade1.stack = "Kotlin"
    var atividade2 = Atividade("Introducao ao Kotlin", Nivel.BASICO, TipoAtividade.AULA)
    atividade2.instrutor = "Julia Gommes"
    atividade2.stack = "Kotlin"
    var atividade3 = Atividade("Materiais de apoio", Nivel.BASICO, TipoAtividade.MATERIAL)
    var atividade4 = Atividade("Certify your knowledge", Nivel.BASICO, TipoAtividade.TESTE)
    atividade4.duracao = 18
    var atividade5 = Atividade("Conhecendo o Javascript e sua documentacao oficial", Nivel.BASICO, duracao=60)
    var atividade6 = Atividade("Introducao pratica a linguagem de programacao Kotlin", Nivel.BASICO, duracao=120)
    var atividade7 = Atividade("Tecnicas avancadas em Nodejs", Nivel.DIFICIL, TipoAtividade.AULA, Acesso.TOP)
    atividade7.duracao = 110
    atividade7.stack = "Javascript"

    var conteudo1 = ConteudoEducacional("Dominando a linguagem de programacao Kotlin")
    var conteudo2 = ConteudoEducacional("POO")

    var formacao1 = Formacao("Kotlin Experience", "01/02/2023")
    var formacao2 = Formacao("C# Developer", "02/10/2023")

    atividade1.pai = 5
    atividade2.pai = 5
    atividade3.pai = 5
    atividade4.pai = 5
    conteudo1.adicionarAtividade(atividade5)
    conteudo1.adicionarAtividade(atividade1)
    conteudo1.adicionarAtividade(atividade2)
    conteudo1.adicionarAtividade(atividade3)
    conteudo1.adicionarAtividade(atividade4)
    conteudo1.adicionarAtividade(atividade6)
    conteudo1.adicionarAtividade(atividade7)

    formacao1.conteudos = listOf(conteudo1, conteudo2)

    var conteudo3 = ConteudoEducacional("Dominando a Linguagem de Programacao Java")
    var atividade8 = Atividade("Dominando IDEs Java", Nivel.INTERMEDIARIO, duracao=49)
    var atividade9 = Atividade("Entendendo metodos Java", Nivel.BASICO, duracao=89)
    var atividade10 = Atividade("Typescript", Nivel.BASICO, TipoAtividade.AULA, Acesso.FREE)
    atividade10.instrutor = "Tex Ranger"
    atividade10.stack = "Java"
    atividade10.pai = 9
    conteudo3.adicionarAtividade(atividade8)
    conteudo3.adicionarAtividade(atividade9)
    conteudo3.adicionarAtividade(atividade10)
    formacao2.conteudos = listOf(conteudo3)

    formacao1.matricular(Usuario("Gomes Adams", LocalDate.of(1987, 10, 27), "Adams"))
    formacao1.matricular(Usuario("Livia Hendrix", LocalDate.of(2000, 6, 18), "LH"))
    formacao1.matricular(Usuario("Kim Kalstrom", LocalDate.of(1999, 9, 23), "K"))
    formacao1.matricular(Usuario("Max Teser", LocalDate.of(1995, 3, 21), "T"))

    formacao1.showTree()

    formacao1.inscritos.forEach { usuario -> println("$usuario ") }
}
