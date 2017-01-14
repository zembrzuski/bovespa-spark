package service

import domain.{IndicadoresStrings, MyUsefulDomain}

object MyDomainToUsefulDomainConverter {

  def fromStringToDouble(input: String): Double = {
    input.replaceAll("\\.", "").replaceAll(",", ".").toDouble
  }

  def convert(input: IndicadoresStrings): MyUsefulDomain = {
    val empresa = input.p3.empresa
    val papel = input.p3.papel
    val setor = input.p1.setor
    val subsetor = input.p1.subsetor
    val pSobreL = fromStringToDouble(input.p1.pSobreL)
    val pSobreVp = fromStringToDouble(input.p1.pSobreVp)
    val lpa = fromStringToDouble(input.p2.lpa)
    val lucroLiquido = fromStringToDouble(input.p2.lucroLiquido)

    MyUsefulDomain(empresa, papel, setor, subsetor, pSobreL, pSobreVp, lpa, lucroLiquido)
  }


}
