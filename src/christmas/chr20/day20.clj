(ns christmas.chr20.day20
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]
            [clojure.pprint :as pp]))

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

(defn edge-frequencies-old [tiles]
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
       (#(find-corners (edge-frequencies-old (vals %)) %))
       (apply *)))



(defn inner-string [string]
  (subs string 1 9))

(defn unpack-inner-tile [tile]
  (->> (:tile tile)
       (rest)
       (butlast)
       (map inner-string)))






(defn edge-frequencies [tiles]
  (->> (mapcat :effective-edges (vals tiles))
       (frequencies)
       (filter #(= 2 (last %)))
       (map first)
       (set)))

(defn corner? [inner-edges {:keys [effective-edges]}]
  (->> (filter inner-edges effective-edges)
       (count)
       (= 2)))

(defn determine-corners [inner-edges tiles]
  (->> (filter (partial corner? inner-edges) (vals tiles))
       (map :number)))

(defn turn [tile]
  (->> (reverse tile)
       (apply map vector)
       (map (partial apply str))))

(defn mirror-new [tile]
  (reverse tile))

(defn turns [tile]
  (->> (:tile tile)
       (iterate turn)
       (take 4)
       (mapcat #(vector % (mirror-new %)))
       (map (partial dfn-tile (:number tile)))))

(defn correcly-oriented? [inner-edges tile]
  (->> (:effective-edges tile)
       (take 2)
       (every? inner-edges)))

(defn add-tile-to-map [information tile]
  (-> (update information :card conj tile)
      (update :tiles dissoc (:number tile))))

(defn prefill-first-corner [{:keys [corners tiles inner-edges] :as information}]
  (->> (first corners)
       (tiles)
       (turns)
       (t/find-first (partial correcly-oriented? inner-edges))
       (add-tile-to-map (assoc information :card []))))

(defn init [tiles]
  (-> (edge-frequencies tiles)
      (#(hash-map :edge-length (int (t/sqrt (count tiles)))
                  :tiles tiles
                  :inner-edges %
                  :corners (determine-corners % tiles)))))

(defn next-horizontal? [{:keys [card inner-edges edge-length]}]
  (-> (count card)
      (mod edge-length)
      (= 0)))

(defn has-edge? [effective-edge tile]
  (->> (:effective-edges tile)
       (set)
       (#(% effective-edge))))

(defn fits? [last-tile [l n] tile]
  (->> (nth (:edges last-tile) l)
       (= (nth (:edges tile) n))))

(defn find-tile [func dirs {:keys [tiles card] :as information}]
  (->> (func card)
       (:effective-edges)
       (#(nth % (first dirs)))
       (#(t/find-first (partial has-edge? %) (vals tiles)))))

(defn next-tile [func dirs {:keys [tiles card] :as information}]
  (->> (find-tile func dirs information)
       (turns)
       (t/find-first (partial fits? (func card) dirs))
       (add-tile-to-map information)))

(defn finish-row [{:keys [edge-length] :as information}]
  (->> (iterate (partial next-tile last [0 2]) information)
       (take edge-length)
       (last)))

(defn start-next-row [{:keys [card edge-length] :as information}]
  (->> (next-tile #(nth % (- (count card) edge-length)) [1 3] information)))

(defn build-row [information]
  (->> (start-next-row information)
       (finish-row)))


(defn build-map [{:keys [card tiles edge-length] :as information}]
  (->> (finish-row information)
       (iterate build-row)
       (t/find-first #(empty? (:tiles %)))))

(def monster? {\# true \. false})

(defn parse-water [row-idx column-idx char]
  [[row-idx column-idx] (monster? char)])

(defn parse-row [row-idx row-line]
  (map-indexed (partial parse-water row-idx) row-line))

(defn reveal-pic [information]
  (->> (:card information)
       (map unpack-inner-tile)
       (partition (:edge-length information))
       (map #(apply map vector %))
       (map (partial map (partial apply str)))
       (flatten)
       (map-indexed parse-row)
       (apply concat)
       (filter last)
       (map first)
       (set)))

(def moster-code ["                  # "
                  "#    ##    ##    ###"
                  " #  #  #  #  #  #   "])

(defn parse-monster []
  (->> (map-indexed parse-row moster-code)
       (apply concat)
       (filter last)
       (map first)
       (map (fn [[x y]] (vector x (- y 18))))))

(def monster (parse-monster))

(defn turn-monster [monster]
  (map (fn [[x y]] (vector y (- x))) monster))

(defn mirror-monster [monster]
  (map (fn [[x y]] (vector (- x) y)) monster))

(def herd
  (->> (iterate turn-monster monster)
       (take 4)
       (mapcat #(vector % (mirror-monster %)))))

(defn apply-monster [monster [wx wy]]
  (map (fn [[mx my]] (vector (+ wx mx) (+ wy my))) monster))

(defn search-monster [waters monster]
  (->> (map (partial apply-monster monster) waters)
       (filter (partial every? waters))
       (apply concat)
       ))

(defn water-roughness [waters]
  (->> (mapcat (partial search-monster waters) herd)
      (reduce disj waters)
       count
  )
       )

(defn day20b [input]
  (->> (tiles input)
       (init)
       (prefill-first-corner)
       (build-map)
       (reveal-pic)
       (water-roughness)))



