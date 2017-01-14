package domain

case class Parte1Strings(tipo: String,
                         nroAcoes: String,
                         pSobreEbit: String,
                         ano2013: String,
                         roe: String,
                         giroAtivos: String,
                         ultimoBalancoProcessado: String,
                         ult12meses: String,
                         psr: String,
                         min52semanas: String,
                         mes: String,
                         subsetor: String,
                         ativo: String,
                         setor: String,
                         pSobreL: String,
                         evSobreEbit: String,
                         ano2012: String,
                         roic: String,
                         valorMercado: String,
                         margEbit: String,
                         disponibilidades: String,
                         pSobreVp: String)

case class Parte2Strings(margemBruta: String,
                         volumeDinheiroMedio2m: String,
                         lucroLiquido: String,
                         ativoCirculante: String,
                         margemLiquida: String,
                         pSobreAtivoCirculante: String,
                         divLiquida: String,
                         lpa: String,
                         cotacao: String,
                         valorFirma: String,
                         ano2016: String,
                         ano2015: String,
                         pSobreAtivos: String,
                         ult30dias: String,
                         divBruta: String,
                         divYield: String,
                         liquidezCorrente: String,
                         dia: String,
                         divBrutaSobrePatrimonio: String,
                         dataUltCotacao: String,
                         ano2014: String,
                         pSobreCapGiro: String)

case class Parte3Strings(papel: String,
                         max52semanas: String,
                         receitaLiquida: String,
                         vpa: String,
                         ebitSobreAtivo: String,
                         ebit: String,
                         crescimentoReceita5anos: String,
                         empresa: String,
                         patrimonioLiquido: String)

case class IndicadoresStrings(p1: Parte1Strings, p2: Parte2Strings, p3: Parte3Strings)