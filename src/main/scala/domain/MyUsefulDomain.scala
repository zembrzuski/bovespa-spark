package domain

import com.esotericsoftware.kryo.serializers.FieldSerializer.Optional

case class MyUsefulDomain(
                           nomeEmpresa: String,
                           siglaEmpresa: String,
                           setor: String,
                           subsetor: String,
                           pSobreL: Double,
                           pSobreVp: Double,
                           lpa: Double,
                           lucroLiquido: Double,
                           patrimonioLiquido: Double
                         )
