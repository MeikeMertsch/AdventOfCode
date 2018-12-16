(defproject christmas "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
  				 [expectations "2.2.0-rc3"]          
  				 [clj-time "0.15.0"]
           [com.rpl/specter "1.1.2"]
           [cheshire "5.8.1"]]
  :main ^:skip-aot christmas.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
