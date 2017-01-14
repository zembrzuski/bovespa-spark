package service

import domain.{Parte1Strings, Parte2Strings, Parte3Strings, IndicadoresStrings}
import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, __}

object MyParser {

  val primeiroRead: Reads[(String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String)] = (
    (__ \ "Tipo").read[String] and
      (__ \ "Nro. Ações").read[String] and
      (__ \ "P/EBIT").read[String] and
      (__ \ "2013").read[String] and
      (__ \ "ROE").read[String] and
      (__ \ "Giro Ativos").read[String] and
      (__ \ "Últ balanço processado").read[String] and
      (__ \ "12 meses").read[String] and
      (__ \ "PSR").read[String] and
      (__ \ "Min 52 sem").read[String] and
      (__ \ "Mês").read[String] and
      (__ \ "Subsetor").read[String] and
      (__ \ "Ativo").read[String] and
      (__ \ "Setor").read[String] and
      (__ \ "P/L").read[String] and
      (__ \ "EV / EBIT").read[String] and
      (__ \ "2012").read[String] and
      (__ \ "ROIC").read[String] and
      (__ \ "Valor de mercado").read[String] and
      (__ \ "Marg. EBIT").read[String] and
      (__ \ "Disponibilidades").read[String] and
      (__ \ "P/VP").read[String]
    ).tupled

  val segundoRead: Reads[(String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String)] = (
    (__ \ "Marg. Bruta").read[String] and
      (__ \ "Vol $ méd (2m)").read[String] and
      (__ \ "Lucro Líquido").read[String] and
      (__ \ "Ativo Circulante").read[String] and
      (__ \ "Marg. Líquida").read[String] and
      (__ \ "P/Ativ Circ Liq").read[String] and
      (__ \ "Dív. Líquida").read[String] and
      (__ \ "LPA").read[String] and
      (__ \ "Cotação").read[String] and
      (__ \ "Valor da firma").read[String] and
      (__ \ "2016").read[String] and
      (__ \ "2015").read[String] and
      (__ \ "P/Ativos").read[String] and
      (__ \ "30 dias").read[String] and
      (__ \ "Dív. Bruta").read[String] and
      (__ \ "Div. Yield").read[String] and
      (__ \ "Liquidez Corr").read[String] and
      (__ \ "Dia").read[String] and
      (__ \ "Div Br/ Patrim").read[String] and
      (__ \ "Data últ cot").read[String] and
      (__ \ "2014").read[String] and
      (__ \ "P/Cap. Giro").read[String]
    ).tupled

  val terceiroRead: Reads[(String, String, String, String, String, String, String, String, String)] = (
    (__ \ "Papel").read[String] and
      (__ \ "Max 52 sem").read[String] and
      (__ \ "Receita Líquida").read[String] and
      (__ \ "VPA").read[String] and
      (__ \ "EBIT / Ativo").read[String] and
      (__ \ "EBIT").read[String] and
      (__ \ "Cres. Rec (5a)").read[String] and
      (__ \ "Empresa").read[String] and
      (__ \ "Patrim. Líq").read[String]
    ).tupled

  val f: (
    (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String),
      (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String),
      (String, String, String, String, String, String, String, String, String)) => IndicadoresStrings = {
    case (
      (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22),
      (b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22),
      (c1, c2, c3, c4, c5, c6, c7, c8, c9)) =>
      IndicadoresStrings(
        Parte1Strings(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22),
        Parte2Strings(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22),
        Parte3Strings(c1, c2, c3, c4, c5, c6, c7, c8, c9)
      )
  }

  implicit val indicadoresReads: Reads[IndicadoresStrings] = (
    primeiroRead and segundoRead and terceiroRead
    ) {
    f
  }


}
