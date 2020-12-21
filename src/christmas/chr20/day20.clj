(ns christmas.chr20.day20
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]
            [clojure.pprint :as pp]
            ))

(defn tile-number [title]
  (t/parse-int (re-find #"\d+" title)))

(defn edge-value [edge]
  (->> (map {\# 1 \. 0} edge)
       (apply str)
       (#(t/parse-int % 2))))

(defn effective-edge-value [edge]
  (->> (map {\# 1 \. 0} edge)
       (#(vector % (reverse %)))
       (map (partial apply str))
       (map #(t/parse-int % 2))
       (apply max)))

(defn edges [tile]
  (->> (apply map vector tile)
       (map (partial apply str))
       (#(vector (last %) (last tile) (first %) (first tile)))))

(defn dfn-tile [number tile]
  (hash-map :edges (map edge-value (edges tile))
            :effective-edges (map effective-edge-value (edges tile))
            :tile tile
            :number number))

(defn raw-tile [[title & tile]]
  (->> (tile-number title)
       (#(vector % (dfn-tile % tile)))))

(defn tiles [file]
  (->> (str/split (slurp file) #"\n\n")
       (map str/split-lines)
       (map raw-tile)
       (apply concat)
       (apply hash-map)))

(defn edge-frequencies [tiles]
  (->> (map :effective-edges tiles)
       (flatten)
       (frequencies)))

(defn is-corner? [all-frequs tile]
  (->> (:effective-edges tile)
       (map all-frequs)
       (filter #{1})
       (count)
       (= 2)))

(defn find-corners [all-frequs tiles]
  (->> (filter #(is-corner? all-frequs (last %)) tiles)
       (map first)))

(defn day20 [input]
  (->> (tiles input)
       (#(find-corners (edge-frequencies (vals %)) %))
       (apply *)))

(defn inner-string [string]
  (subs string 1 9))

(defn unpack-inner-tile [tile]
  (->> (:tile tile)
       (rest)
       (butlast)
       (map inner-string)))

(defn orientate-top-left-corner [corner {:keys [frequs tiles]}]
  (->> (tiles corner)
       (:tile)
       (reverse)
       (dfn-tile corner)))

(defn initialize-with-corner [information first-corner]
  (-> (assoc information :card [[first-corner]])
      (update :tiles #(dissoc % (:number first-corner)))))

(defn find-next-eff-edge [information]
  (->> (:card information)
       (t/llast)
       (:effective-edges)
       (first)))

(defn turn-right [tile]
  (->> (:tile tile)
       (reverse)
       (apply map vector)
       (map (partial apply str))
       (dfn-tile (:number tile))))

(defn mirror [{:keys [tile number]}]
  (->> (reverse tile)
       (dfn-tile number)))

(defn mirror-if-needed [eff-edge tile]
  (if ((set (:effective-edges tile)) eff-edge)
    (mirror tile)
    tile))

(defn orientate-tile [eff-edge pos tile]
  (->> (:effective-edges tile)
       (take-while #(not= eff-edge %))
       (count)
       (#(- % pos))
       (#(if (neg? %) (+ 4 %) %))
       (nth (iterate turn-right tile))
       (mirror-if-needed eff-edge)))

(defn find-tile-by-edge [information eff-edge]
  (->> (:tiles information)
       (t/find-first #(contains? (set (:effective-edges (last %))) eff-edge))
       (last)))

(defn next-tile [information eff-edge]
  (->> (find-tile-by-edge information eff-edge)
       (orientate-tile eff-edge 2)))

(defn add-tile[information tile]
  (-> (update information :tiles #(dissoc % (:number tile)))
      (update :card #(t/add-to-last % tile))))

(defn add-to-map [information]
  (->> (find-next-eff-edge information)
       (next-tile information)
       (add-tile information)))

(defn build-map [information]
  (->> (orientate-top-left-corner (first (:corners information)) information)
       (initialize-with-corner information)
       (iterate add-to-map)
       (#(nth % 2))))

(defn day20b [input]
  (->> (tiles input)
       (hash-map :tiles)
       (#(assoc % :frequs (edge-frequencies (vals (:tiles %)))))
       (#(assoc % :corners (find-corners (:frequs %) (:tiles %))))
       (build-map)
       ;:tiles
       ;keys
       ;(:card)
       ;(first)
       ;(map :tile)
       ;(apply map #(str/join " " %&))
       ;;(pp/pprint)
       ;(map #(str/join " " %))
       ;first
       ;(#(find-corners (edge-frequencies %) %))
       ))
