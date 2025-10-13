# kafka-genetic-to-mtb-xml

This project implement a kafka-biconsumer which construct the required xml that include the genetic information, which will be sent to MTB. The required xml will be constructed by collecting the genetic information which are scatted in different kafka-topics.

## Embedded list of genes

This project contains a list of 43000 genes taken from
[https://genenames.org](https://www.genenames.org/cgi-bin/download/custom?col=gd_hgnc_id&col=gd_app_sym&col=gd_app_name&col=gd_pub_chrom_map&col=md_ensembl_id&status=Approved&hgnc_dbtag=on&order_by=gd_app_sym_sort&format=text&submit=submit).

The list of genes is available under the [Creative Commons Public Domain (CC0) License](https://creativecommons.org/public-domain/cc0/) and is used to complete genetic information.